# Zabbix Java

## 模块

* [zabbix-build](.) 构建控制
* [zabbix-protocol](zabbix-protocol) 协议基础
* [zabbix-sender](zabbix-sender) Sender实现
* [zabbix-spring-boot-autoconfigure](zabbix-spring-boot-autoconfigure) Spring Boot 自动配置
* [zabbix-spring-boot-starter](zabbix-spring-boot-starter) Spring Boot Starter
* [zabbix-bom](zabbix-bom) Bill of materials (BOM)

### 一般用法

```java
import cn.bromine0x23.zabbix.sender.ZabbixSender;
import cn.bromine0x23.zabbix.sender.ZabbixSenderResponse;

public class ZabbixSenderExample {

	private static final String SERVER_HOST = "132.102.99.115"; // Zabbix服务器地址

	private static final String HOST = "example"; // 主机名

	public static void main(String[] arguments) {

		ZabbixSender sender = ZabbixSender.create(SERVER_HOST, HOST);

		ZabbixSenderResponse response; 

		response = sender.send(sender.requestBuilder().datum("java.test", "test").build());

		System.out.println(response);

		response = sender.send(
			sender.requestBuilder()
				.datum("java.test.0", "test0")
				.datum("java.test.1", "test1")
				.datum("java.test.2", "test2")
				.build()
		);

		System.out.println(response);
	}
}
```

