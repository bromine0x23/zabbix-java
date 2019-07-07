/*
 * Copyright © 2018-2019 Bromine0x23 <bromine0x23@163.com>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package cn.bromine0x23.zabbix.protocol.netty;

import cn.bromine0x23.zabbix.protocol.domain.ZabbixRequest;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Zabbix请求编码器
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
public class ZabbixRequestEncoder extends MessageToByteEncoder<ZabbixRequest> {

	private ObjectWriter objectWriter;

	public ZabbixRequestEncoder(ObjectWriter objectWriter) {
		this.objectWriter = objectWriter;
	}

	@Override
	protected void encode(ChannelHandlerContext context, ZabbixRequest request, ByteBuf out) throws Exception {
		out.writeBytes(objectWriter.writeValueAsBytes(request));
	}
}
