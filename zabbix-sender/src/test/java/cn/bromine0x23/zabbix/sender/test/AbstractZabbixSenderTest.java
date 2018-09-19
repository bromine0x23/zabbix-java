package cn.bromine0x23.zabbix.sender.test;

import cn.bromine0x23.zabbix.sender.ZabbixSender;
import cn.bromine0x23.zabbix.sender.domain.ZabbixSenderRequest;
import cn.bromine0x23.zabbix.sender.domain.ZabbixSenderResponse;

import java.time.Instant;

import static org.junit.Assert.*;

/**
 * Created on 18/8/30.
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
abstract class AbstractZabbixSenderTest {

	protected void doTestSend(ZabbixSender sender) throws Exception {
		ZabbixSenderResponse response;

		response = sender.send(ZabbixSenderRequest.builder().datum("127.0.0.1", "java.test", "test").build());
		assertNotNull(response);
		assertEquals(1, response.getTotal());

		response = sender.send(
			ZabbixSenderRequest.builder()
				.datum("127.0.0.1", "java.test.1", "test1")
				.datum("127.0.0.1", "java.test.2", "test2", Instant.now())
				.build()
		);
		assertNotNull(response);
		assertEquals(2, response.getTotal());

		response = sender.send(
			sender.requestBuilder()
				.datum("java.test.0", "test0")
				.datum("java.test.1", "test1", Instant.now())
				.datum("192.168.0.1", "java.test.2", "test2", Instant.now())
				.build()
		);
		assertNotNull(response);
		assertEquals(3, response.getTotal());
	}
}