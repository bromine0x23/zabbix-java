/*
 * Copyright © 2018-2019 Bromine0x23 <bromine0x23@163.com>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package cn.bromine0x23.zabbix.protocol.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldPrepender;

import java.util.List;

import static cn.bromine0x23.zabbix.protocol.ZabbixProtocolConstants.*;

/**
 * Zabbix帧编码器
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
public class ZabbixFrameEncoder extends LengthFieldPrepender {

	public ZabbixFrameEncoder() {
		super(
			BYTE_ORDER,
			LENGTH_BYTES,
			0,
			false
		);
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		out.add(ctx.alloc().buffer(HEADER_BYTES).writeBytes(HEADER));
		super.encode(ctx, msg, out);
	}
}
