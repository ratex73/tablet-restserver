package kr.co.yedaham.tablet.restserver.restserver.model.funCalc;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
//TBFU1004
public class FunItemInfo {
    private FunItemId funItemId;
    private String state;
    private String grpCd;
    private Integer  qty;
    private Integer  amt;
    private String creatYn;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime regDate;
    private String regId;
    private LocalDateTime updateDate;
    private String updateId;
    private String payBackCode;
    private String payBackComment;

}
