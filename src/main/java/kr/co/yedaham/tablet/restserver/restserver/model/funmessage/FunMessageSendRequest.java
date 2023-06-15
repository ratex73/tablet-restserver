package kr.co.yedaham.tablet.restserver.restserver.model.funmessage;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FunMessageSendRequest {
    private String mournerPhone;
    private String funCtrlNo;
    private String seq;
    private String deadNm;
    private String memo;
    private String mourNm;
    private String funNm;
}
