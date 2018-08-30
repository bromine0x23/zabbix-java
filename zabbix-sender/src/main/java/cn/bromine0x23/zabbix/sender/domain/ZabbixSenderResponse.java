package cn.bromine0x23.zabbix.sender.domain;

import cn.bromine0x23.zabbix.protocol.domain.ZabbixResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Zabbix Sender 请求
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ZabbixSenderResponse extends ZabbixResponse<ZabbixSenderResponse.Payload> {

	public ZabbixSenderResponse() {
	}

	@Data
	public static class Payload {

		@JsonProperty("info")
		private String info;
	}
}
