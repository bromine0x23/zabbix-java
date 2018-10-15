package cn.bromine0x23.zabbix.sender.impl;

import cn.bromine0x23.zabbix.protocol.ZabbixProtocolConstants;
import cn.bromine0x23.zabbix.sender.domain.ZabbixSenderRequest;
import cn.bromine0x23.zabbix.sender.domain.ZabbixSenderResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

/**
 * Zabbix Sender Socket实现
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@Slf4j
public class SocketZabbixSender extends AbstractZabbixSender {

	private SocketZabbixSender(String serverHost, int serverPort, String defaultHost, ObjectMapper objectMapper) {
		super(serverHost, serverPort, defaultHost, objectMapper);
	}

	public static Builder builder() {
		return new Builder();
	}

	public ZabbixSenderResponse send(ZabbixSenderRequest request, Duration timeout) throws IOException {
		try (Socket socket = new Socket()) {
			socket.setSoTimeout((int)timeout.toMillis());
			socket.connect(new InetSocketAddress(getServerHost(), getServerPort()), (int)timeout.toMillis());

			writeRequest(socket.getOutputStream(), request);

			ZabbixSenderResponse response = readResponse(socket.getInputStream());

			log.debug("response = {}", response);

			return response;
		}
	}

	private void writeRequest(OutputStream output, ZabbixSenderRequest request) throws IOException {
		byte[] bodyBuffer   = getObjectMapper().writeValueAsBytes(request);
		int    length       = bodyBuffer.length;
		byte[] lengthBuffer = new byte[8];
		lengthBuffer[0] = (byte)((length >>> 0x00) & 0xFF);
		lengthBuffer[1] = (byte)((length >>> 0x08) & 0xFF);
		lengthBuffer[2] = (byte)((length >>> 0x10) & 0xFF);
		lengthBuffer[3] = (byte)((length >>> 0x18) & 0xFF);
		lengthBuffer[4] = 0;
		lengthBuffer[5] = 0;
		lengthBuffer[6] = 0;
		lengthBuffer[7] = 0;
		output.write(ZabbixProtocolConstants.HEADER);
		output.write(lengthBuffer);
		output.write(bodyBuffer);
		output.flush();
	}

	private ZabbixSenderResponse readResponse(InputStream input) throws IOException {
		int read;

		// 读取头
		byte[] headerBuffer = new byte[ZabbixProtocolConstants.HEADER_BYTES];
		read = input.read(headerBuffer);
		assert read == headerBuffer.length;
		assert Arrays.equals(ZabbixProtocolConstants.HEADER, headerBuffer);

		// 读取长度
		byte[] lengthBuffer = new byte[ZabbixProtocolConstants.LENGTH_BYTES];
		read = input.read(lengthBuffer);
		assert read == lengthBuffer.length;

		int length = 0;
		length |= lengthBuffer[0] << 0x00;
		length |= lengthBuffer[1] << 0x08;
		length |= lengthBuffer[2] << 0x10;
		length |= lengthBuffer[3] << 0x18;

		byte[] bodyBuffer = new byte[length];
		read = input.read(bodyBuffer);
		assert read == bodyBuffer.length;

		return getObjectMapper().readValue(bodyBuffer, ZabbixSenderResponse.class);
	}

	public static class Builder extends AbstractZabbixSender.Builder<Builder, SocketZabbixSender> {

		@Override
		public SocketZabbixSender build() {
			if (getServerHost() == null) {
				throw new IllegalArgumentException("`serverHost` cannot be null");
			}
			return new SocketZabbixSender(
				getServerHost(),
				getServerPort(),
				getDefaultHost(),
				Optional.ofNullable(getObjectMapper()).orElseGet(ObjectMapper::new)
			);
		}
	}
}
