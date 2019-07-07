/*
 * Copyright © 2018-2019 Bromine0x23 <bromine0x23@163.com>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package cn.bromine0x23.zabbix.sender.test;

import cn.bromine0x23.zabbix.sender.ZabbixSender;
import cn.bromine0x23.zabbix.sender.ZabbixSenderConstants;
import cn.bromine0x23.zabbix.sender.impl.NettyZabbixSender;
import cn.bromine0x23.zabbix.sender.impl.SocketZabbixSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * ZabbixSender 测试
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@RunWith(JUnit4.class)
public class ZabbixSenderTest {

	@Test
	public void testConstruct() {
		String serverHost = "104.24.103.152";
		int serverPort = 20051;
		{
			ZabbixSender sender = ZabbixSender.create(serverHost);
			assertEquals(serverHost, sender.getServerHost());
			assertEquals(ZabbixSenderConstants.DEFAULT_ACTIVE_PORT, sender.getServerPort());
		} {
			ZabbixSender sender = ZabbixSender.create(serverHost, serverPort);
			assertEquals(serverHost, sender.getServerHost());
			assertEquals(serverPort, sender.getServerPort());
		}
	}

	@Test
	public void testSocketConstruct() {
		String serverHost = "104.24.103.152";
		int serverPort = 20051;
		{
			ZabbixSender sender = ZabbixSender.socket(serverHost);
			assertEquals(serverHost, sender.getServerHost());
			assertEquals(ZabbixSenderConstants.DEFAULT_ACTIVE_PORT, sender.getServerPort());
			assertThat(sender, instanceOf(SocketZabbixSender.class));
		} {
			ZabbixSender sender = ZabbixSender.socket(serverHost, serverPort);
			assertEquals(serverHost, sender.getServerHost());
			assertEquals(serverPort, sender.getServerPort());
			assertThat(sender, instanceOf(SocketZabbixSender.class));
		}
	}

	@Test
	public void testNettyConstruct() {
		String serverHost = "104.24.103.152";
		int serverPort = 20051;
		{
			ZabbixSender sender = ZabbixSender.netty(serverHost);
			assertEquals(serverHost, sender.getServerHost());
			assertEquals(ZabbixSenderConstants.DEFAULT_ACTIVE_PORT, sender.getServerPort());
			assertThat(sender, instanceOf(NettyZabbixSender.class));
		} {
			ZabbixSender sender = ZabbixSender.netty(serverHost, serverPort);
			assertEquals(serverHost, sender.getServerHost());
			assertEquals(serverPort, sender.getServerPort());
			assertThat(sender, instanceOf(NettyZabbixSender.class));
		}
	}
}