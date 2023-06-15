package kr.co.yedaham.tablet.restserver.restserver.resp.comm;

import kr.co.yedaham.tablet.restserver.restserver.entity.CommCemeEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.comm.CommCemeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommCemeResp extends JpaRepository <CommCemeEntity,Long> {


    @Query(name = "getCommCemeList", nativeQuery = true)
    List<CommCemeList> getCommCemeList(@Param("funCd") String funCd);
}
