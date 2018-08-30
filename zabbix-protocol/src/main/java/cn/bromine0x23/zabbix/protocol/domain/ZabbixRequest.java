package cn.bromine0x23.zabbix.protocol.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 请求
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@EqualsAndHashCode
@ToString
public class ZabbixRequest<TPayload> {

	@Getter
	@JsonProperty("request")
	private String request;

	@Getter
	@Setter(AccessLevel.PROTECTED)
	@JsonUnwrapped
	private TPayload payload;

	protected ZabbixRequest(String request, TPayload payload) {
		this.request = request;
		this.payload = payload;
	}
}
