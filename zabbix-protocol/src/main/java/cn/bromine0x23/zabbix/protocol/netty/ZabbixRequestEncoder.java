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