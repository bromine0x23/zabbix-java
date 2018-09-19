package cn.bromine0x23.zabbix.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created on 18/9/19.
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@Data
@ConfigurationProperties("zabbix")
public class ZabbixProperties {
}
