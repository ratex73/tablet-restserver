package kr.co.yedaham.tablet.restserver.restserver.config.sms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix="sms")
public class SmsProperties {
    private String msgkey;
    private String telNo;
}
