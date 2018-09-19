package cn.bromine0x23.zabbix.autoconfigure.sender;

import cn.bromine0x23.zabbix.sender.ZabbixSender;
import cn.bromine0x23.zabbix.sender.impl.NettyZabbixSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.bootstrap.Bootstrap;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
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
@ConditionalOnClass(Bootstrap.class)
@Conditional(ZabbixSenderCondition.class)
public class NettyZabbixSenderConfiguration {

	private final ZabbixSenderProperties properties;

	private final ObjectMapper objectMapper;

	public NettyZabbixSenderConfiguration(
		ZabbixSenderProperties properties,
		ObjectProvider<ObjectMapper> objectMapper
	) {
		this.properties = properties;
		this.objectMapper = objectMapper.getIfAvailable(ObjectMapper::new);
	}

	@Bean
	@ConditionalOnMissingBean
	public NettyZabbixSender zabbixSender() {
		NettyZabbixSender.Builder builder = NettyZabbixSender.builder();

		PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
		propertyMapper.from(properties::getServerHost).to(builder::serverHost);
		propertyMapper.from(properties::getServerPort).to(builder::serverPort);
		propertyMapper.from(properties::getDefaultHost).to(builder::defaultHost);

		builder.objectMapper(objectMapper);

		return builder.build();
	}
}
