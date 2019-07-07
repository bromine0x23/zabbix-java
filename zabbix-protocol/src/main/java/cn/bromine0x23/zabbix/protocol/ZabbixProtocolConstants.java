/*
 * Copyright © 2018-2019 Bromine0x23 <bromine0x23@163.com>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package cn.bromine0x23.zabbix.protocol;

import lombok.experimental.UtilityClass;

import java.nio.ByteOrder;

/**
 * 常量
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@UtilityClass
public class ZabbixProtocolConstants {

	public static final byte[] HEADER = {'Z', 'B', 'X', 'D', '\01'}; // 帧头

	public static final ByteOrder BYTE_ORDER = ByteOrder.LITTLE_ENDIAN; // 字节序：小端序

	public static final int HEADER_BYTES   = HEADER.length; // 帧头长度
	public static final int LENGTH_BYTES   = Long.BYTES; // 长度域长度
	public static final int MAX_BODY_BYTES = 0x08000000; // 最大帧长：128MB
}
