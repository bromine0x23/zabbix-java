package cn.bromine0x23.zabbix.sender.domain;

import cn.bromine0x23.zabbix.protocol.domain.ZabbixRequest;
import cn.bromine0x23.zabbix.sender.ZabbixSenderConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Zabbix Sender 请求
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ZabbixSenderRequest extends ZabbixRequest<ZabbixSenderRequest.Payload> {

	protected ZabbixSenderRequest(Payload payload) {
		super(ZabbixSenderConstants.ZABBIX_SENDER_REQUEST, payload);
	}

	private ZabbixSenderRequest(List<Datum> data, Instant instant) {
		super(ZabbixSenderConstants.ZABBIX_SENDER_REQUEST, Payload.of(data, instant.getEpochSecond(), instant.getNano()));
	}

	public static Builder builder() {
		return new Builder();
	}

	public static Builder builder(String defaultHost) {
		return new Builder(defaultHost);
	}

	@Value(staticConstructor = "of")
	public static class Payload {

		@JsonProperty("data")
		private List<Datum> data;

		@JsonProperty("clock")
		private Long timestamp;

		@JsonProperty("ns")
		private Integer ns;
	}

	@Value(staticConstructor = "of")
	public static class Datum {

		@JsonProperty("host")
		private String host;

		@JsonProperty("key")
		private String key;

		@JsonProperty("value")
		private String value;

		@JsonProperty("clock")
		private Long timestamp;
	}

	@ToString
	public static class Builder {

		private final String defaultHost;

		private List<Datum> data = new ArrayList<>();

		private Builder() {
			this("127.0.0.1");
		}

		private Builder(String defaultHost) {
			this.defaultHost = defaultHost;
		}

		public Builder datum(Datum datum) {
			this.data.add(datum);
			return this;
		}

		public Builder datum(String key, String value) {
			return this.datum(defaultHost, key, value);
		}

		public Builder datum(String key, String value, Instant timestamp) {
			return this.datum(defaultHost, key, value, timestamp);
		}

		public Builder datum(String host, String key, String value) {
			return this.datum(host, key, value, Instant.now());
		}

		public Builder datum(String host, String key, String value, Instant timestamp) {
			return this.datum(Datum.of(host, key, value, timestamp.getEpochSecond()));
		}

		public Builder data(Datum... data) {
			this.data = new ArrayList<>(Arrays.asList(data));
			return this;
		}

		public Builder data(List<Datum> data) {
			this.data = data;
			return this;
		}

		public ZabbixSenderRequest build() {
			return new ZabbixSenderRequest(this.data, Instant.now());
		}
	}
}
