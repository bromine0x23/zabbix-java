package cn.bromine0x23.zabbix.protocol.netty;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import static cn.bromine0x23.zabbix.protocol.ZabbixProtocolConstants.*;

/**
 * Zabbix帧解码器
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
public class ZabbixFrameDecoder extends LengthFieldBasedFrameDecoder {

	public ZabbixFrameDecoder() {
		super(
			BYTE_ORDER,
			MAX_BODY_BYTES,
			HEADER_BYTES,
			LENGTH_BYTES,
			0,
			HEADER_BYTES + LENGTH_BYTES,
			true
		);
	}
}
