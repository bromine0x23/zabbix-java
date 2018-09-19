package cn.bromine0x23.zabbix.autoconfigure.sender;

import cn.bromine0x23.zabbix.sender.ZabbixSender;
import cn.bromine0x23.zabbix.sender.impl.SocketZabbixSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 18/9/19.
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@Configuration
@ConditionalOnMissingBean(ZabbixSender.class)
@Conditional(ZabbixSenderCondition.class)
public class SocketZabbixSenderConfiguration {

	private final ZabbixSenderProperties properties;

	private final ObjectMapper objectMapper;

	public SocketZabbixSenderConfiguration(
		ZabbixSenderProperties properties,
		ObjectProvider<ObjectMapper> objectMapper
	) {
		this.properties = properties;
		this.objectMapper = objectMapper.getIfAvailable(ObjectMapper::new);
	}

	@Bean
	@ConditionalOnMissingBean
	public SocketZabbixSender zabbixSender() {
		SocketZabbixSender.Builder builder = SocketZabbixSender.builder();

		PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
		propertyMapper.from(properties::getServerHost).to(builder::serverHost);
		propertyMapper.from(properties::getServerPort).to(builder::serverPort);
		propertyMapper.from(properties::getDefaultHost).to(builder::defaultHost);

		builder.objectMapper(objectMapper);
		return builder.build();
	}
}
