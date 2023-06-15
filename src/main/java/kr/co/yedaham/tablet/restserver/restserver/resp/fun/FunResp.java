package kr.co.yedaham.tablet.restserver.restserver.resp.fun;

import kr.co.yedaham.tablet.restserver.restserver.entity.FunEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.fun.*;
import kr.co.yedaham.tablet.restserver.restserver.model.funmessage.FunMessageSigninInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface FunResp extends JpaRepository<FunEntity, String> {

    @Query(name = "getSubcontractorPhoneList", nativeQuery = true)
    ArrayList<Subcontractor> getSubcontractPhoneList(@Param("userid") String userid);

    @Query(name = "getSubcontractorFunList", nativeQuery = true)
    ArrayList<SubcontractorFunList> getSubcontractFunList(@Param("userid") String userid);

    @Query(name = "findGetFunCarInfo", nativeQuery = true)
    FunCarInfo findGetFunCarInfo(@Param("functrlno") String functrlno);

    @Query(name = "findFunProgressInfo", nativeQuery = true)
    FunProgressInfo findFunProgressInfo(@Param("functrlno") String functrlno);

    @Query(name = "findFunCustomerInfo", nativeQuery = true)
    FunCustomerInfo findFunCustomerInfo(@Param("functrlno") String functrlno);

    @Query(name = "findFunFuneralInfo1", nativeQuery = true)
    FunFuneralInfo1 findFunFuneralInfo1(@Param("functrlno") String functrlno);

    @Query(name = "findFunFuneralInfo2", nativeQuery = true)
    FunFuneralInfo2 findFunFuneralInfo2(@Param("functrlno") String functrlno);

    @Query(name = "findFunWorkerInfo", nativeQuery = true)
    FunWorkerInfo findFunWorkerInfo(@Param("functrlno") String functrlno);

    @Query(name = "getFunStartSmsEmpList", nativeQuery = true)
    ArrayList<FunSmsEmpList> getFunStartSmsEmpList(@Param("functrlno") String functrlno);

    @Query(name = "getFunEndSmsEmpList", nativeQuery = true)
    ArrayList<FunSmsEmpList> getFunEndSmsEmpList(@Param("functrlno") String functrlno);

    public FunEntity findByFunCtrlNoAndStatus(@Param("funno") String funno,@Param("status") String status);

    @Query(name = "getFunFuneralFirstHelpInfo", nativeQuery = true)
    FunMessageSigninInfo getFunFuneralFirstHelpInfo(@Param("phone") String phone);
}
