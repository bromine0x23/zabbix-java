/*
 * Copyright © 2018-2019 Bromine0x23 <bromine0x23@163.com>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
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