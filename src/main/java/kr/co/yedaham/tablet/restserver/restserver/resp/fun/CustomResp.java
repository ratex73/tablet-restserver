package kr.co.yedaham.tablet.restserver.restserver.resp.fun;


import kr.co.yedaham.tablet.restserver.restserver.model.fun.FunList;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomResp {
    private final EntityManager em;

    public List<FunList> getFunList(String query){

        JpaResultMapper result = new JpaResultMapper();
        Query nativeQuery = em.createNativeQuery(query);
        List<FunList> list = result.list(nativeQuery, FunList.class);
        return list;
    }
}
