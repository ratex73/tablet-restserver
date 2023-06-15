package kr.co.yedaham.tablet.restserver.restserver.resp.sms;

import kr.co.yedaham.tablet.restserver.restserver.entity.TabletSmsEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.sms.FunSmsEnd;
import kr.co.yedaham.tablet.restserver.restserver.model.sms.FunSmsPhone;
import kr.co.yedaham.tablet.restserver.restserver.model.sms.FunSmsStart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SmsResp extends JpaRepository<TabletSmsEntity, Integer> {
    @Query(name = "findFunSendSms", nativeQuery = true)
    FunSmsPhone findFunSendSms(@Param("functrlno") String functrlno);

    @Query(name = "findFunSmsStart", nativeQuery = true)
    FunSmsStart findFunSmsStart(@Param("functrlno") String functrlno);

    @Query(name = "findFunSmsEnd", nativeQuery = true)
    FunSmsEnd findFunSmsEnd(@Param("functrlno") String functrlno);
}
