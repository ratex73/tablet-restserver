package kr.co.yedaham.tablet.restserver.restserver.resp.comm;


import kr.co.yedaham.tablet.restserver.restserver.entity.ContractAmtEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.comm.FunCoComList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface CommResp extends JpaRepository<ContractAmtEntity, String> {

    @Query(name = "findFunCoComList", nativeQuery = true)
    ArrayList<FunCoComList> findFunCoComList(@Param("fungb") String fungb, @Param("frareacd") String frareacd, @Param("toareacd") String toareacd);

    @Query(name = "findFunCoComListByName", nativeQuery = true)
    ArrayList<FunCoComList> findFunCoComListByName(@Param("funnm") String funnm, @Param("addr") String addr, @Param("telno") String telno);
}
