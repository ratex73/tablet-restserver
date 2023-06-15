package kr.co.yedaham.tablet.restserver.restserver.service.commute;


import kr.co.yedaham.tablet.restserver.restserver.entity.CommuteEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.commute.*;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;

import java.util.List;
import java.util.Optional;

public interface CommuteService {

    public List<Commute> getCommuteList(CommuteRequest commuteRequest);
    public CommuteSaveInfo commuteStartSaveInfo(CommuteSaveInfo commuteSaveInfo);
    public CommuteSaveInfo commuteEndSaveInfo(CommuteSaveInfo commuteSaveInfo);
    public List<DeptInfo> getDeptList(String userId);
    public List<CommuteCancelInfo> getCommuteCancelList(String userId);
    public List<CommuteFdInfo> getCommuteFdList(String funCtrlNo);
    public ResponseService updateCommuteFunDays(long seq, String funDays);
}
