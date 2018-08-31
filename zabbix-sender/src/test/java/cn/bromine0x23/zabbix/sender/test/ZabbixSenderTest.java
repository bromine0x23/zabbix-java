package cn.bromine0x23.zabbix.sender.test;

import cn.bromine0x23.zabbix.sender.ZabbixSender;
import cn.bromine0x23.zabbix.sender.domain.ZabbixSenderRequest;
import cn.bromine0x23.zabbix.sender.domain.ZabbixSenderResponse;
import cn.bromine0x23.zabbix.sender.impl.NettyZabbixSender;
import cn.bromine0x23.zabbix.sender.impl.SocketZabbixSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * Created on 18/8/30.
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@RunWith(JUnit4.class)
public class ZabbixSenderTest extends AbstractZabbixSenderTest {

	@Test
	public void test() throws Exception {
		ZabbixSender sender = ZabbixSender.create("132.102.99.115");

		doTest(sender);
	}

	@Test
	public void testSocket() throws Exception {
		ZabbixSender sender = ZabbixSender.socket("132.102.99.115");

		assertThat(sender, instanceOf(SocketZabbixSender.class));

		doTest(sender);
	}

	@Test
	public void testNetty() throws Exception {
		ZabbixSender sender = ZabbixSender.netty("132.102.99.115");

		assertThat(sender, instanceOf(NettyZabbixSender.class));

		doTest(sender);
	}

}