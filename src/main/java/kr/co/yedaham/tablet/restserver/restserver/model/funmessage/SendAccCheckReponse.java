package kr.co.yedaham.tablet.restserver.restserver.model.funmessage;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendAccCheckReponse {
    @JsonAlias(value = "Response_code")
    private String reponseCode;
    @JsonAlias(value = "Response_desc")
    private String responseDesc;
    @JsonAlias(value = "ACC_HOLD")
    private String accHold;
}
