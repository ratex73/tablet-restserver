package kr.co.yedaham.tablet.restserver.restserver.resp.commute;


import kr.co.yedaham.tablet.restserver.restserver.model.commute.Commute;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomCommuteResp {

    private  final EntityManager em;

    public List<Commute> getCommuteList(String query) {

        JpaResultMapper result = new JpaResultMapper();
        Query nativeQuery = em.createNativeQuery(query);
        List<Commute> list = result.list(nativeQuery, Commute.class);

        return list;

    }
}
