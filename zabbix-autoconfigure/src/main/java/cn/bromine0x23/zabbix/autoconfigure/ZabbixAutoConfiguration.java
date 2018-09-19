package cn.bromine0x23.zabbix.autoconfigure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 18/9/19.
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@Configuration
@EnableConfigurationProperties(ZabbixProperties.class)
public class ZabbixAutoConfiguration {
}
