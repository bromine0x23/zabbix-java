package cn.bromine0x23.zabbix.sender.domain;

import cn.bromine0x23.zabbix.protocol.domain.ZabbixResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Zabbix Sender 请求
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ZabbixSenderResponse extends ZabbixResponse<ZabbixSenderResponse.Payload> {

	private static final Pattern INFO_PATTERN = Pattern.compile(
		"processed: (?<succeed>\\d+); failed: (?<failed>\\d+); total: (?<total>\\d+); seconds spent: (?<seconds>\\d+\\.\\d+)"
	);

	@JsonIgnore
	@ToString.Exclude
	private boolean parsed = false;

	@JsonIgnore
	@ToString.Exclude
	private int succeed = 0;

	@JsonIgnore
	@ToString.Exclude
	private int failed = 0;

	@JsonIgnore
	@ToString.Exclude
	private int total = 0;

	public ZabbixSenderResponse() {
	}

	public int getSucceed() {
		parseInfo();
		return succeed;
	}

	public int getFailed() {
		parseInfo();
		return failed;
	}

	public int getTotal() {
		parseInfo();
		return total;
	}

	private void parseInfo() {
		if (!isParsed()) {
			if (getPayload() != null && getPayload().getInfo() != null) {
				Matcher matcher = INFO_PATTERN.matcher(getPayload().getInfo());
				if (matcher.find()) {
					this.succeed = Integer.parseInt(matcher.group("succeed"));
					this.failed = Integer.parseInt(matcher.group("failed"));
					this.total = Integer.parseInt(matcher.group("total"));
				}
			}
			parsed = true;
		}
	}

	@Data
	public static class Payload {

		@JsonProperty("info")
		private String info;
	}
}
