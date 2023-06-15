package kr.co.yedaham.tablet.restserver.restserver.service.comm;

import kr.co.yedaham.tablet.restserver.restserver.model.comm.CommCemeList;
import kr.co.yedaham.tablet.restserver.restserver.model.comm.FunCoComList;
import kr.co.yedaham.tablet.restserver.restserver.model.response.ListResult;
import kr.co.yedaham.tablet.restserver.restserver.resp.comm.CommCemeResp;
import kr.co.yedaham.tablet.restserver.restserver.resp.comm.CommResp;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommServiceImpl implements CommService {
    private final CommResp commResp;
    private final CommCemeResp commCemeResp;

    private final EntityManager em;

    @Override
    public List<FunCoComList> findFunCoComList(String fungb, String frareacd, String toareacd) {
        ArrayList<FunCoComList> funCoComList = null;
        funCoComList = commResp.findFunCoComList(fungb, frareacd, toareacd);

        return funCoComList;
    }

    @Override
    public List<FunCoComList> findFunCoComListByName(String funnm, String addr, String telno) {
        ArrayList<FunCoComList> funCoComList = null;
        funCoComList = commResp.findFunCoComListByName(funnm, addr, telno);
        return funCoComList;
    }

    @Override
    public List<CommCemeList> getCommCemeList(String funCd) {
        List<CommCemeList> commCemeList = commCemeResp.getCommCemeList(funCd);

        return commCemeList;
    }

}
