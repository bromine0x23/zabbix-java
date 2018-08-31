package cn.bromine0x23.zabbix.sender.test;

import cn.bromine0x23.zabbix.sender.ZabbixSender;
import cn.bromine0x23.zabbix.sender.impl.NettyZabbixSender;
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
public class NettyZabbixSenderTest extends AbstractZabbixSenderTest {

	@Test
	public void test() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		ZabbixSender sender = NettyZabbixSender.builder().host("132.102.99.115").objectMapper(objectMapper).build();

		doTest(sender);
	}

}