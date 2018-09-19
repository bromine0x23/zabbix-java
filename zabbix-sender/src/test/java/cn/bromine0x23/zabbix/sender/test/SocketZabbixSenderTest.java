package cn.bromine0x23.zabbix.sender.test;

import cn.bromine0x23.zabbix.sender.ZabbixSender;
import cn.bromine0x23.zabbix.sender.impl.SocketZabbixSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created on 18/8/30.
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@RunWith(JUnit4.class)
public class SocketZabbixSenderTest extends AbstractZabbixSenderTest {

	@Test
	public void testSend() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		ZabbixSender sender = SocketZabbixSender.builder().serverHost("132.102.99.115").objectMapper(objectMapper).build();

		doTestSend(sender);
	}
}