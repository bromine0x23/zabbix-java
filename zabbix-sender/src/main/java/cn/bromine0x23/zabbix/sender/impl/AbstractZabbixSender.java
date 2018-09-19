package cn.bromine0x23.zabbix.sender.impl;

import cn.bromine0x23.zabbix.sender.ZabbixSender;
import cn.bromine0x23.zabbix.sender.domain.ZabbixSenderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;

import static cn.bromine0x23.zabbix.sender.ZabbixSenderConstants.*;

/**
 * Created on 18/9/19.
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
public abstract class AbstractZabbixSender implements ZabbixSender {

	@Getter
	private final String serverHost;

	@Getter
	private final int serverPort;

	@Getter
	private final String defaultHost;

	@Getter(AccessLevel.PROTECTED)
	private final ObjectMapper objectMapper;

	protected AbstractZabbixSender(String serverHost, int serverPort, String defaultHost, ObjectMapper objectMapper) {
		this.serverHost = serverHost;
		this.serverPort = serverPort;
		this.defaultHost = defaultHost;
		this.objectMapper = objectMapper;
	}

	@Override
	public ZabbixSenderRequest.Builder requestBuilder() {
		return defaultHost != null ? ZabbixSenderRequest.builder(defaultHost) : ZabbixSenderRequest.builder();
	}

	@SuppressWarnings("unchecked")
	public static abstract class Builder<TDerived extends Builder, TZabbixSender extends ZabbixSender> {

		@Getter(AccessLevel.PROTECTED)
		private String serverHost;

		@Getter(AccessLevel.PROTECTED)
		private int serverPort = DEFAULT_ACTIVE_PORT;

		@Getter(AccessLevel.PROTECTED)
		private String defaultHost;

		@Getter(AccessLevel.PROTECTED)
		private ObjectMapper objectMapper;

		public TDerived serverHost(String serverHost) {
			this.serverHost = serverHost;
			return (TDerived) this;
		}

		public TDerived serverPort(int serverPort) {
			this.serverPort = serverPort;
			return (TDerived) this;
		}

		public TDerived defaultHost(String defaultHost) {
			this.defaultHost = defaultHost;
			return (TDerived) this;
		}

		public TDerived objectMapper(ObjectMapper objectMapper) {
			this.objectMapper = objectMapper;
			return  (TDerived) this;
		}

		public abstract TZabbixSender build();
	}
}
