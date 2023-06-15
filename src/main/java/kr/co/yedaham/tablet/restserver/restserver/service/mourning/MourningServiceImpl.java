package kr.co.yedaham.tablet.restserver.restserver.service.mourning;

import kr.co.yedaham.tablet.restserver.restserver.entity.FunEntity;
import kr.co.yedaham.tablet.restserver.restserver.entity.MourningEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.comm.MourningFunDayTimeInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.fun.FunDayTimeInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.mourning.MourningInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.response.SingleResult;
import kr.co.yedaham.tablet.restserver.restserver.resp.fun.FunResp;
import kr.co.yedaham.tablet.restserver.restserver.resp.mourning.MourningResp;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MourningServiceImpl implements MourningService{
    private final MourningResp mourningResp;
    private final FunResp funResp;

    private final ResponseService responseService;


    @Override
    public SingleResult<MourningFunDayTimeInfo> saveMourning(MourningFunDayTimeInfo mourningFunDayTimeInfo) {
        try {

            FunEntity funEntity = funResp.findByFunCtrlNoAndStatus(mourningFunDayTimeInfo.getFunDayTimeInfo().getFunno(), mourningFunDayTimeInfo.getFunDayTimeInfo().getStatus());
            funEntity.setCortSupYn(mourningFunDayTimeInfo.getFunDayTimeInfo().getCortSupYn());
            funEntity.setCortSupNcd(mourningFunDayTimeInfo.getFunDayTimeInfo().getCortSupNcd());
            funResp.save(funEntity);

            Integer maxSeq = mourningResp.findMaxSeq(mourningFunDayTimeInfo.getMourningInfo().getMourningId().getFunCtrlNo());
            MourningEntity mourningEntitie = mourningResp.findByMourningIdFunCtrlNoAndUseYnAndDelYn(mourningFunDayTimeInfo.getMourningInfo().getMourningId().getFunCtrlNo(), 'Y', 'N');
            if(mourningEntitie != null && maxSeq > 1) {
                mourningEntitie.setUseYn('N');
                mourningEntitie.setUpdateId(mourningFunDayTimeInfo.getMourningInfo().getUpdateId());
                MourningEntity save = mourningResp.save(mourningEntitie);
            }
            mourningFunDayTimeInfo.getMourningInfo().getMourningId().setSeq(maxSeq);
            MourningEntity build = mourningResp.save(MourningEntity.builder().mourningInfo(mourningFunDayTimeInfo.getMourningInfo()).build());

            mourningFunDayTimeInfo.setFunDayTimeInfo(updateFunDayTimeInfo(mourningFunDayTimeInfo.getFunDayTimeInfo()));
        } catch (Exception ex){
            responseService.getFailResult(9999, ex.getMessage());
        }

        return responseService.getSingleResult(mourningFunDayTimeInfo);
    }

    private FunDayTimeInfo updateFunDayTimeInfo(FunDayTimeInfo funDayTimeInfo) {

        FunDayTimeInfo result = new FunDayTimeInfo();
        FunEntity update = funResp.findById(funDayTimeInfo.getFunno()).get();
        if(update != null) {
            update.setArrvDate(funDayTimeInfo.getArrvDate());
            update.setArrvTime(funDayTimeInfo.getArrvTime());
            update.setArrvMin(funDayTimeInfo.getArrvMin());
            update.setDeadDate(funDayTimeInfo.getDeadDate());
            update.setDeadTime(funDayTimeInfo.getDeadTime());
            update.setDeadMin(funDayTimeInfo.getDeadMin());
            update.setCaskDate(funDayTimeInfo.getCaskDate());
            update.setCaskTime(funDayTimeInfo.getCaskTime());
            update.setCaskMin(funDayTimeInfo.getCaskMin());
            update.setCortDate(funDayTimeInfo.getCortDate());
            update.setCortTime(funDayTimeInfo.getCortTime());
            update.setCortMin(funDayTimeInfo.getCortMin());
            update.setFunStartDate(funDayTimeInfo.getFunStartDate());
            update.setFunStartTime(funDayTimeInfo.getFunStartTime());
            update.setFunStartMin(funDayTimeInfo.getFunStartMin());
            update.setFunEndDate(funDayTimeInfo.getFunEndDate());
            update.setFunEndTime(funDayTimeInfo.getFunEndTime());
            update.setFunEndMin(funDayTimeInfo.getFunEndMin());
            update.setFunDays(funDayTimeInfo.getFunDays());
            update.setProdArrvDate(funDayTimeInfo.getProdArrvDate());
            update.setProdArrvTime(funDayTimeInfo.getProdArrvTime());
            update.setProdArrvMin(funDayTimeInfo.getProdArrvMin());

            FunEntity save = funResp.save(update);
            result.setArrvDate(save.getArrvDate());
            result.setArrvTime(save.getArrvTime());
            result.setArrvMin(save.getArrvMin());
            result.setDeadDate(save.getDeadDate());
            result.setDeadTime(save.getDeadTime());
            result.setDeadMin(save.getDeadMin());
            result.setCaskDate(save.getCaskDate());
            result.setCaskTime(save.getCaskTime());
            result.setCaskMin(save.getCaskMin());
            result.setCortDate(save.getCortDate());
            result.setCortTime(save.getCortTime());
            result.setCortMin(save.getCortMin());
            result.setFunStartDate(save.getFunStartDate());
            result.setFunStartTime(save.getFunStartTime());
            result.setFunStartMin(save.getFunStartMin());
            result.setFunEndDate(save.getFunEndDate());
            result.setFunEndTime(save.getFunEndTime());
            result.setFunEndMin(save.getFunEndMin());
            result.setFunDays(save.getFunDays());
            result.setProdArrvDate(save.getProdArrvDate());
            result.setProdArrvDate(save.getProdArrvTime());
            result.setProdArrvDate(save.getProdArrvMin());
        }
        return result;
    }
}
