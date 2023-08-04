package kr.co.yedaham.tablet.restserver.restserver;

import com.ubireport.server.UbiServer4;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UbiServerServletConfing {

	@Bean
	public ServletRegistrationBean<UbiServer4> getServletRegistrationBean() {
		ServletRegistrationBean<UbiServer4> registrationBean = new ServletRegistrationBean<>(new UbiServer4());

		registrationBean.addInitParameter("isAbsolutePath", "true");
		registrationBean.addInitParameter("propertyPath", "/ubireport/UbiService");

		registrationBean.addUrlMappings("/UbiServer");
		return registrationBean;
	}

}