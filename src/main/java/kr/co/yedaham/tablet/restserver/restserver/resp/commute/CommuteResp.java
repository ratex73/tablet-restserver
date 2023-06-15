package kr.co.yedaham.tablet.restserver.restserver.resp.commute;

import kr.co.yedaham.tablet.restserver.restserver.entity.CommuteEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.commute.CommuteCancelInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.commute.CommuteFdInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.commute.DeptInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CommuteResp extends JpaRepository<CommuteEntity, Long> {

    @Query(value = "select nvl(MAX(SEQ)+1,1) from YDHRCH010M", nativeQuery = true)
    public int getNextValMySequence();

    @Query(name = "getDeptList", nativeQuery = true)
    List<DeptInfo> getDeptList(@Param("userId") String userId);


    @Query(name = "getCommuteCancelList", nativeQuery = true)
    List<CommuteCancelInfo> getCommuteCancelList(@Param("userId") String userId);


     @Query(name = "getCommuteFdList", nativeQuery = true)
    List<CommuteFdInfo> getCommuteFdList(@Param("funCtrlNo") String funCtrlNo);

}
