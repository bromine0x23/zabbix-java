package cn.bromine0x23.zabbix.sender;

import lombok.experimental.UtilityClass;

import java.time.Duration;

/**
 * Zabbix Sender 常量
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@UtilityClass
public class ZabbixSenderConstants {

	public static final String ZABBIX_SENDER_REQUEST = "sender data";

	public static final int DEFAULT_ACTIVE_PORT = 10051;

	public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(3);
}