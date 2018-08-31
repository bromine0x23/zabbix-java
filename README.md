# Zabbix Java

##

```java
public class ZabbixSenderExample {
	
	public static void main(String[] arguments) {
		ZabbixSender sender = ZabbixSender.socket("132.102.99.115");
		
		ZabbixSenderResponse response; 
		
		response = sender.send(ZabbixSenderRequest.builder().datum("127.0.0.1", "java.test", "test").build());
		
		System.out.println(response);
		
		response = sender.send(
			ZabbixSenderRequest.builder()
				.datum("127.0.0.1", "java.test.0", "test0")
				.datum("127.0.0.1", "java.test.1", "test1")
				.datum("127.0.0.1", "java.test.2", "test2")
				.build()
		);
		System.out.println(response);
	}
	
}
```