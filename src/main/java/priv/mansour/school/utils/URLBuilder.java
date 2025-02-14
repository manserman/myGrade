package priv.mansour.school.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class URLBuilder {
	@Autowired
	private HttpServletRequest request;

	public String getRegion() {
		String host = request.getServerName();
		if (host.contains(".")) {
			return host.split("\\.")[0];
		}
		return "fr";
	}

	public Builder newBuilder() {
		return new Builder(getRegion());
	}

	public static class Builder {
		private final String region;
		private String serviceName;
		private String domain;
		private int port = -1;
		private String protocol = null;
		private String format = "{service}-{region}.{domain}";

		public Builder(String region) {
			this.region = region;
		}

		public Builder service(String serviceName) {
			this.serviceName = serviceName;
			return this;
		}

		public Builder domain(String domain) {
			this.domain = domain;
			return this;
		}

		public Builder port(int port) {
			this.port = port;
			return this;
		}

		public Builder protocol(String protocol) {
			this.protocol = protocol;
			return this;
		}

		public Builder format(String format) {
			this.format = format;
			return this;
		}

		public String build() {
			if (serviceName == null || domain == null) {
				throw new IllegalStateException("ServiceName et Domain doivent être définis !");
			}

			String hostname = format.replace("{service}", serviceName).replace("{region}", region).replace("{domain}",
					domain);

			StringBuilder url = new StringBuilder();
			if (protocol != null) {
				url.append(protocol).append("://");
			}
			url.append(hostname);
			if (port > 0) {
				url.append(":").append(port);
			}

			return url.toString();
		}
	}
}
