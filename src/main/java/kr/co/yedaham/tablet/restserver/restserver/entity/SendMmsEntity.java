package kr.co.yedaham.tablet.restserver.restserver.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="T_MMS_SD", schema = "TK_SMS")
public class SendMmsEntity {
    @Id
    private String msgKey;
    private char msgtype;
    private String calleeNo;
    private String callbackNo;
    private String mmsMsg;
    private String subject;
    private String etc1;

    @Builder
    public SendMmsEntity(String msgKey, char msgtype, String calleeNo, String callbackNo, String mmsMsg,String etc1) {
        Assert.hasText(msgKey, "msgKey must not be empty");
        Assert.hasText(calleeNo, "calleeNo must not be empty");
        Assert.hasText(mmsMsg, "mmsMsg must not be empty");
        this.msgKey = msgKey;
        this.msgtype = msgtype;
        this.calleeNo = calleeNo;
        this.callbackNo = callbackNo;
        this.mmsMsg = mmsMsg;
        this.subject = "[예다함]";
        this.etc1 = etc1;
    }
}
