package kr.co.yedaham.tablet.restserver.restserver.model.funmessage;


import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@RequiredArgsConstructor
public class FunMessageMours {
    private long historyNo;
    private long seq;
    private String funCtrlNo;
    private LocalDateTime regDate;
    private String regId;
    private LocalDateTime updateDate;
    private String updateId;
    private String deadReat;
    private String mourReatNm;
    private String mourReatAccNo;
    private String mourReatBankNm;
    private long funSeq;
    private long mourReatOrder;
    private String mourReatPhone;
    private String mourAccJuminNo;
    private String mainYn;
    private String flag;
    private LocalDateTime historyDate;

}
