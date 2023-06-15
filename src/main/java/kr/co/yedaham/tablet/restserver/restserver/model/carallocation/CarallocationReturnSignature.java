package kr.co.yedaham.tablet.restserver.restserver.model.carallocation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CarallocationReturnSignature {
    private long carReturnId;
    private String funCtrlNo;
    private String signature;
    private String regEmpno;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime regDate;
    private String deliveryArea;
    private String etcMemo;
}
