package kr.co.yedaham.tablet.restserver.restserver.model.funCalc;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class FunItemInfo {

    private FunItemId funItemId;
    private String state;
    private String grpCd;
    private int qty;
    private int amt;
    private String creatYn;
    private LocalDateTime regDate;
    private String regId;
    private LocalDateTime updateDate;
    private String updateId;
    private String payBackCode;
    private String payBackComment;

}
