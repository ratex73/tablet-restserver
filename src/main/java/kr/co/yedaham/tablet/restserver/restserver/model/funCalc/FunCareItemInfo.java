package kr.co.yedaham.tablet.restserver.restserver.model.funCalc;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class FunCareItemInfo {
    private Integer seq;
    private String funCtrlNo;
    private Integer cSeq;
    private String careItemCd;
    private char useYn;
    private char delYn;
    private LocalDateTime regDate;
    private String regId;
    private LocalDateTime updateDate;
    private String updateId;
}
