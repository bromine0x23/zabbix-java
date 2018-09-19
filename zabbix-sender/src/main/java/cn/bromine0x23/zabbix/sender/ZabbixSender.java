package cn.bromine0x23.zabbix.sender;

import cn.bromine0x23.zabbix.sender.domain.ZabbixSenderRequest;
import cn.bromine0x23.zabbix.sender.domain.ZabbixSenderResponse;
import cn.bromine0x23.zabbix.sender.impl.NettyZabbixSender;
import cn.bromine0x23.zabbix.sender.impl.SocketZabbixSender;

import java.time.Duration;

import static cn.bromine0x23.zabbix.sender.ZabbixSenderConstants.*;

/**
 * Zabbix Sender 接口
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
public interface ZabbixSender {

	Duration DEFAULT_TIMEOUT = Duration.ofSeconds(3);

	/**
	 * @return Zabbix服务器地址
	 */
	String getServerHost();

	/**
	 * @return Zabbix服务器（主动模式）端口
	 */
	int getServerPort();

	/**
	 * @return 默认主机名
	 */
	default String getDefaultHost() {
		return null;
	}

	default ZabbixSenderRequest.Builder requestBuilder() {
		return ZabbixSenderRequest.builder();
	}

	/**
	 * 发送数据
	 *
	 * @param request 请求
	 * @param timeout 超时时间
	 * @return 响应
	 * @throws Exception 异常
	 */
	ZabbixSenderResponse send(ZabbixSenderRequest request, Duration timeout) throws Exception;

	default ZabbixSenderResponse send(ZabbixSenderRequest request) throws Exception {
		return send(request, DEFAULT_TIMEOUT);
	}

	static ZabbixSender create(String serverHost) {
		return socket(serverHost);
	}

	static ZabbixSender create(String serverHost, int serverPort) {
		return socket(serverHost, serverPort);
	}

	static ZabbixSender socket(String serverHost) {
		return socket(serverHost, DEFAULT_ACTIVE_PORT);
	}

	static ZabbixSender socket(String serverHost, int serverPort) {
		return socket(serverHost, serverPort, null);
	}

	static ZabbixSender socket(String serverHost, int serverPort, String defaultHost) {
		return SocketZabbixSender.builder()
			.serverHost(serverHost)
			.serverPort(serverPort)
			.defaultHost(defaultHost)
			.build();
	}

	static ZabbixSender netty(String serverHost) {
		return netty(serverHost, DEFAULT_ACTIVE_PORT);
	}

	static ZabbixSender netty(String serverHost, int serverPort) {
		return netty(serverHost, serverPort, null);
	}

	static ZabbixSender netty(String serverHost, int serverPort, String defaultHost) {
		return NettyZabbixSender.builder()
			.serverHost(serverHost)
			.serverPort(serverPort)
			.defaultHost(defaultHost)
			.build();
	}
}
