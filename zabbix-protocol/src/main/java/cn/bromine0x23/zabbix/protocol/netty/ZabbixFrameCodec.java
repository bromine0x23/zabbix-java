/*
 * Copyright © 2018-2019 Bromine0x23 <bromine0x23@163.com>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package cn.bromine0x23.zabbix.protocol.netty;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * Zabbix帧编解码器
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
public class ZabbixFrameCodec extends CombinedChannelDuplexHandler<ZabbixFrameDecoder, ZabbixFrameEncoder> {

	public ZabbixFrameCodec() {
		super(new ZabbixFrameDecoder(), new ZabbixFrameEncoder());
	}
}
