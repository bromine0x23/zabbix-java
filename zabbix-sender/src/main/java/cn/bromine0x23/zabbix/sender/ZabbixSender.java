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
	String getHost();

	/**
	 * @return Zabbix服务器（主动模式）端口
	 */
	int getPort();

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

	static ZabbixSender create(String host) {
		return socket(host);
	}

	static ZabbixSender create(String host, int port) {
		return socket(host, port);
	}

	static ZabbixSender socket(String host) {
		return socket(host, DEFAULT_ACTIVE_PORT);
	}

	static ZabbixSender socket(String host, int port) {
		return SocketZabbixSender.builder().host(host).port(port).build();
	}

	static ZabbixSender netty(String host) {
		return netty(host, DEFAULT_ACTIVE_PORT);
	}

	static ZabbixSender netty(String host, int port) {
		return NettyZabbixSender.builder().host(host).port(port).build();
	}
}
