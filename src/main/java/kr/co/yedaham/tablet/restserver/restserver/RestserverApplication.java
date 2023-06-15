package kr.co.yedaham.tablet.restserver.restserver;

import kr.co.yedaham.tablet.restserver.restserver.config.file.FileUploadProperties;
import kr.co.yedaham.tablet.restserver.restserver.config.sms.SendProperties;
import kr.co.yedaham.tablet.restserver.restserver.config.sms.SmsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({
		FileUploadProperties.class, SmsProperties.class, SendProperties.class
})

public class RestserverApplication {
	public static void main(String[] args) {
		SpringApplication.run(RestserverApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
		}
}