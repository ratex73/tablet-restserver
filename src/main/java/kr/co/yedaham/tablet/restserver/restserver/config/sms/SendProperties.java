package kr.co.yedaham.tablet.restserver.restserver.config.sms;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix="send")
public class SendProperties {
    private String url;
    private String funMessageUrl;
}
