package cn.bromine0x23.zabbix.protocol.netty;

import cn.bromine0x23.zabbix.protocol.domain.ZabbixResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Zabbix请求编码器
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
public class ZabbixResponseDecoder<T extends ZabbixResponse> extends ByteToMessageDecoder {

	private ObjectReader objectReader;

	public ZabbixResponseDecoder(ObjectMapper objectMapper, Class<T> klass) {
		this.objectReader = objectMapper.readerFor(klass);
	}

	public ZabbixResponseDecoder(ObjectReader objectReader) {
		this.objectReader = objectReader;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf message, List<Object> out) throws Exception {
		byte[] array = new byte[message.readableBytes()];
		message.readBytes(array);
		T response = objectReader.readValue(array);
		out.add(response);
	}
}
