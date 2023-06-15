package kr.co.yedaham.tablet.restserver.restserver.service.updatehistory;

import kr.co.yedaham.tablet.restserver.restserver.entity.UpdateHistoryEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;
import kr.co.yedaham.tablet.restserver.restserver.model.response.ListResult;
import kr.co.yedaham.tablet.restserver.restserver.model.updatehistory.UpdateHistoryList;
import kr.co.yedaham.tablet.restserver.restserver.resp.updatehistory.UpdateHistoryResp;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateHistoryServiceImpl implements UpdateHistoryService {
    private final UpdateHistoryResp updateHistoryResp;
    private final ModelMapper modelMapper;

    private final ResponseService responseService; // 결과를 처리할 Service

    @Override
    public List<UpdateHistoryList> findUpdateHistoryList(String updateid, String funno) {
        ArrayList<UpdateHistoryList> updateHistoryList = updateHistoryResp.findUpdateHistoryList(updateid, funno);
        return updateHistoryList;
    }
    @Override
    public CommonResult insertUpdateInfo(UpdateHistoryList updateInfo) {
        UpdateHistoryList updateHistory = new UpdateHistoryList();
        try {
            UpdateHistoryEntity saveEntity = updateHistoryResp.save(UpdateHistoryEntity.builder().updateHistoryList(updateInfo).build());
            updateHistory = modelMapper.map(saveEntity, UpdateHistoryList.class);
        } catch (Exception ex) {
            responseService.getFailResult(9999, ex.getMessage());
        }
        return responseService.getSingleResult(updateHistory);
    }

}
