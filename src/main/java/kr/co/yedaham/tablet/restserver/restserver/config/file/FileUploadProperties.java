package kr.co.yedaham.tablet.restserver.restserver.config.file;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix="file")
public class FileUploadProperties {
    private String uploadDir;
}
