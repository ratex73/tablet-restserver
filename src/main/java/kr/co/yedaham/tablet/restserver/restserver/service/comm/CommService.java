package kr.co.yedaham.tablet.restserver.restserver.service.comm;

import kr.co.yedaham.tablet.restserver.restserver.model.comm.CommCemeList;
import kr.co.yedaham.tablet.restserver.restserver.model.comm.FunCoComList;
import kr.co.yedaham.tablet.restserver.restserver.model.response.ListResult;

import java.util.ArrayList;
import java.util.List;

public interface CommService {
    public List<FunCoComList> findFunCoComList(String fungb, String frareacd, String toareacd);
    public List<FunCoComList> findFunCoComListByName(String funnm, String addr, String telno);

    public List<CommCemeList> getCommCemeList(String funCd);
}
