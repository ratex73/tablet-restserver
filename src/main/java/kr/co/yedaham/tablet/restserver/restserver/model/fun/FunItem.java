package kr.co.yedaham.tablet.restserver.restserver.model.fun;


import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.FunItemId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@RequiredArgsConstructor
public class FunItem {

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
    private String prodMainCd;
    private String payBackCode;
    private String payBackComment;
}
