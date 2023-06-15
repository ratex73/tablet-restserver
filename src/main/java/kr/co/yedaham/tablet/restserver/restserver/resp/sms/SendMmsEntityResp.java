package kr.co.yedaham.tablet.restserver.restserver.resp.sms;

import kr.co.yedaham.tablet.restserver.restserver.entity.SendMmsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface SendMmsEntityResp extends JpaRepository<SendMmsEntity, Integer> {
    @Query(value = "SELECT TK_SMS.SEQ_T_MMS_SD.nextval FROM dual", nativeQuery = true)
    public BigDecimal getNextValMySequence();
}
