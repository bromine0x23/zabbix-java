package cn.bromine0x23.zabbix.autoconfigure.sender;

import org.springframework.util.Assert;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * Zabbix Sender Mappings
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
public class ZabbixSenderMappings {

	private static final Map<ZabbixSenderType, Class<?>> MAPPINGS;

	static {
		Map<ZabbixSenderType, Class<?>> mappings = new EnumMap<>(ZabbixSenderType.class);
		mappings.put(ZabbixSenderType.SOCKET, SocketZabbixSenderConfiguration.class);
		mappings.put(ZabbixSenderType.NETTY, NettyZabbixSenderConfiguration.class);
		MAPPINGS = Collections.unmodifiableMap(mappings);
	}

	public static String getConfigurationClass(ZabbixSenderType turnstileType) {
		Class<?> configurationClass = MAPPINGS.get(turnstileType);
		Assert.state(configurationClass != null, () -> "Unknown cache type " + turnstileType);
		return configurationClass.getName();
	}

	public static ZabbixSenderType getType(String configurationClassName) {
		for (Map.Entry<ZabbixSenderType, Class<?>> entry : MAPPINGS.entrySet()) {
			if (Objects.equals(entry.getValue().getName(), configurationClassName)) {
				return entry.getKey();
			}
		}
		throw new IllegalStateException("Unknown configuration class " + configurationClassName);
	}

}
