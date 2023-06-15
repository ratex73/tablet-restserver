package kr.co.yedaham.tablet.restserver.restserver.service.updatehistory;

import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;
import kr.co.yedaham.tablet.restserver.restserver.model.response.ListResult;
import kr.co.yedaham.tablet.restserver.restserver.model.updatehistory.UpdateHistoryList;
import kr.co.yedaham.tablet.restserver.restserver.model.updatehistory.UpdateHistoryRequest;
import kr.co.yedaham.tablet.restserver.restserver.model.updatehistory.UpdateInfo;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;

import java.util.List;

public interface UpdateHistoryService {
    public List<UpdateHistoryList> findUpdateHistoryList(String updateid, String funno);
    public CommonResult insertUpdateInfo(UpdateHistoryList updateHistoryList);
}
