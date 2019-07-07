/*
 * Copyright © 2018-2019 Bromine0x23 <bromine0x23@163.com>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package cn.bromine0x23.zabbix.sender.impl;

import cn.bromine0x23.zabbix.protocol.netty.ZabbixFrameCodec;
import cn.bromine0x23.zabbix.protocol.netty.ZabbixRequestEncoder;
import cn.bromine0x23.zabbix.protocol.netty.ZabbixResponseDecoder;
import cn.bromine0x23.zabbix.sender.domain.ZabbixSenderRequest;
import cn.bromine0x23.zabbix.sender.domain.ZabbixSenderResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Zabbix Sender Netty实现
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@Slf4j
public class NettyZabbixSender extends AbstractZabbixSender {

	private final EventLoopGroup workerGroup;

	private final Bootstrap bootstrap;

	private NettyZabbixSender(
		String serverHost, int serverPort, String defaultHost, ObjectMapper objectMapper, EventLoopGroup workerGroup
	) {
		super(serverHost, serverPort, defaultHost, objectMapper);
		this.workerGroup = workerGroup;
		this.bootstrap = createBootstrap();
	}

	public ZabbixSenderResponse send(ZabbixSenderRequest request, Duration timeout) throws InterruptedException {
		ChannelFuture channelFuture = bootstrap.connect(getServerHost(), getServerPort());

		if (timeout == null) {
			channelFuture.await();
		} else {
			channelFuture.await(timeout.toNanos(), TimeUnit.NANOSECONDS);
		}

		Channel channel = channelFuture.channel();

		ResponseReceiver receiver = new ResponseReceiver();
		channel.writeAndFlush(request);
		channel.pipeline().addLast(receiver);
		channel.read();

		ZabbixSenderResponse response = receiver.poll(timeout);

		channel.close();
		return response;
	}

	public static Builder builder() {
		return new Builder();
	}

	private Bootstrap createBootstrap() {
		return new Bootstrap()
			.group(workerGroup)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel channel) {
					channel.pipeline()
						.addLast(new LoggingHandler(LogLevel.DEBUG))
						.addLast(new ZabbixFrameCodec())
						.addLast(new ZabbixRequestEncoder(getObjectMapper().writer()))
						.addLast(new ZabbixResponseDecoder<>(getObjectMapper().readerFor(ZabbixSenderResponse.class)))
					;
				}
			});
	}

	private class ResponseReceiver extends ChannelInboundHandlerAdapter {

		private BlockingQueue<ZabbixSenderResponse> queue = new ArrayBlockingQueue<>(1);

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			ZabbixSenderResponse response = (ZabbixSenderResponse)msg;
			log.debug("response = {}", response);
			queue.offer(response);
			super.channelRead(ctx, msg);
		}

		private ZabbixSenderResponse poll(Duration timeout) throws InterruptedException {
			if (timeout != null) {
				return queue.poll(timeout.toNanos(), TimeUnit.NANOSECONDS);
			} else {
				return queue.take();
			}
		}
	}

	public static class Builder extends AbstractZabbixSender.Builder<Builder, NettyZabbixSender> {

		private EventLoopGroup workerGroup;

		public Builder workerGroup(EventLoopGroup workerGroup) {
			this.workerGroup = workerGroup;
			return this;
		}

		@Override
		public NettyZabbixSender build() {
			if (getServerHost() == null) {
				throw new IllegalArgumentException("`serverHost` cannot be null");
			}
			return new NettyZabbixSender(
				getServerHost(),
				getServerPort(),
				getDefaultHost(),
				Optional.ofNullable(getObjectMapper()).orElseGet(ObjectMapper::new),
				Optional.ofNullable(workerGroup).orElseGet(NioEventLoopGroup::new)
			);
		}
	}
}
