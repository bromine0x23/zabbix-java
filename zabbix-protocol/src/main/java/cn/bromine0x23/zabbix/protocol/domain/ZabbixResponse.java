package cn.bromine0x23.zabbix.protocol.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import lombok.Getter;

/**
 * 响应
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@Data
public class ZabbixResponse<TPayload> {

	@Getter
	@JsonProperty("response")
	private String response;

	@Getter
	@JsonUnwrapped
	private TPayload payload;
}
