package cn.bromine0x23.zabbix.sender.impl;

import cn.bromine0x23.zabbix.protocol.netty.ZabbixFrameDecoder;
import cn.bromine0x23.zabbix.protocol.netty.ZabbixFrameEncoder;
import cn.bromine0x23.zabbix.protocol.netty.ZabbixRequestEncoder;
import cn.bromine0x23.zabbix.protocol.netty.ZabbixResponseDecoder;
import cn.bromine0x23.zabbix.sender.ZabbixSender;
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
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static cn.bromine0x23.zabbix.sender.ZabbixSenderConstants.*;

/**
 * Zabbix Sender Netty实现
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@Slf4j
public class NettyZabbixSender implements ZabbixSender {

	@Getter
	private final String host;

	@Getter
	private final int port;

	private final ObjectMapper objectMapper;

	private final EventLoopGroup workerGroup;

	private final Bootstrap bootstrap;

	private NettyZabbixSender(
		String host, int port, ObjectMapper objectMapper, EventLoopGroup workerGroup
	) {
		this.host = host;
		this.port = port;
		this.objectMapper = objectMapper;
		this.workerGroup = workerGroup;
		this.bootstrap = createBootstrap();
	}

	public ZabbixSenderResponse send(ZabbixSenderRequest request, Duration timeout) throws InterruptedException {
		ChannelFuture channelFuture = bootstrap.connect(host, port);

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
						.addLast(new LoggingHandler(LogLevel.TRACE))
						.addLast(new ZabbixFrameEncoder())
						.addLast(new ZabbixRequestEncoder(objectMapper))
						.addLast(new ZabbixFrameDecoder())
						.addLast(new ZabbixResponseDecoder<>(objectMapper, ZabbixSenderResponse.class))
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

	public static class Builder {

		private String host;

		private int port = DEFAULT_ACTIVE_PORT;

		private ObjectMapper objectMapper;

		private EventLoopGroup workerGroup;

		public Builder host(String host) {
			this.host = host;
			return this;
		}

		public Builder port(int port) {
			this.port = port;
			return this;
		}

		public Builder objectMapper(ObjectMapper objectMapper) {
			this.objectMapper = objectMapper;
			return this;
		}

		public Builder workerGroup(EventLoopGroup workerGroup) {
			this.workerGroup = workerGroup;
			return this;
		}

		public NettyZabbixSender build() {
			if (host == null) {
				throw new IllegalArgumentException("`host` cannot be null");
			}
			return new NettyZabbixSender(
				host,
				port,
				objectMapper == null ? new ObjectMapper() : objectMapper,
				workerGroup == null ? new NioEventLoopGroup() : workerGroup
			);
		}
	}
}
