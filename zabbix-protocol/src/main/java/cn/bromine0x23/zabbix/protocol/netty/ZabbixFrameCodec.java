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
