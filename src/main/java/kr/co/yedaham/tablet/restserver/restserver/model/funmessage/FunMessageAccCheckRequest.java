package kr.co.yedaham.tablet.restserver.restserver.model.funmessage;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.Assert;

import javax.persistence.Column;

@Data
@AllArgsConstructor
public class FunMessageAccCheckRequest {
    private String accNo;
    private String accJuminNo;
    private String inBankCd;
    private String reqChanel;
    private String accHold;
    private String accTel;
    private String userId;
}
