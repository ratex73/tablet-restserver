package kr.co.yedaham.tablet.restserver.restserver.model.funCalc;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class FunItemInfoM {
    private String funCtrlNo;
    private int funBalCnt;
    private String mainGb;
    private String assiProdCd;
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
