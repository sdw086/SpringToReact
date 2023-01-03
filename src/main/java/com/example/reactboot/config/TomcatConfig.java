package com.example.reactboot.config;

import com.example.reactboot.common.utils.PropertyUtil;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {
	private final PropertyUtil propertyUtil;

	public TomcatConfig(PropertyUtil propertyUtil) {
		this.propertyUtil = propertyUtil;
	}

	@Bean
	public ServletWebServerFactory serverFactory() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		tomcat.addAdditionalTomcatConnectors(createAjpConnector());
		return tomcat;
	}

	private Connector createAjpConnector() {
		Connector ajpConnector = new Connector(propertyUtil.getPropery("tomcat.ajp.protocol"));
		ajpConnector.setPort(propertyUtil.getPropertyInt("tomcat.ajp.port"));
		ajpConnector.setSecure(false);
		ajpConnector.setAllowTrace(false);
		ajpConnector.setScheme(propertyUtil.getPropery("tomcat.ajp.scheme"));
		((AbstractAjpProtocol) ajpConnector.getProtocolHandler()).setSecretRequired(false);

		return ajpConnector;
	}
}
