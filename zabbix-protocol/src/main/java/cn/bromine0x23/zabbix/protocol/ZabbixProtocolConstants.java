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
