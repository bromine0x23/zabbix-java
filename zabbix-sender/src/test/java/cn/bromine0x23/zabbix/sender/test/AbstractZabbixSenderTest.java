package cn.bromine0x23.zabbix.sender.test;

import cn.bromine0x23.zabbix.sender.ZabbixSender;
import cn.bromine0x23.zabbix.sender.domain.ZabbixSenderRequest;
import cn.bromine0x23.zabbix.sender.domain.ZabbixSenderResponse;

import static org.junit.Assert.*;

/**
 * Created on 18/8/30.
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
abstract class AbstractZabbixSenderTest {

	protected void doTest(ZabbixSender sender) throws Exception {
		ZabbixSenderResponse response;

		response = sender.send(ZabbixSenderRequest.builder().datum("127.0.0.1", "java.test", "test").build());
		assertNotNull(response);

		response = sender.send(
			ZabbixSenderRequest.builder()
				.datum("127.0.0.1", "java.test.0", "test0")
				.datum("127.0.0.1", "java.test.1", "test1")
				.datum("127.0.0.1", "java.test.2", "test2")
				.build()
		);
		assertNotNull(response);
	}

}