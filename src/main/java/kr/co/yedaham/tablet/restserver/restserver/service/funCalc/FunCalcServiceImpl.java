package kr.co.yedaham.tablet.restserver.restserver.service.funCalc;

import kr.co.yedaham.tablet.restserver.restserver.entity.CarallocationInfoEntity;
import kr.co.yedaham.tablet.restserver.restserver.entity.FunCalcEntity;
import kr.co.yedaham.tablet.restserver.restserver.entity.FunCareItemEntity;
import kr.co.yedaham.tablet.restserver.restserver.entity.FunItemEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.*;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;
import kr.co.yedaham.tablet.restserver.restserver.resp.funCalc.FunCalcResp;
import kr.co.yedaham.tablet.restserver.restserver.resp.funCalc.FunCareItemResp;
import kr.co.yedaham.tablet.restserver.restserver.resp.funCalc.FunItemResp;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FunCalcServiceImpl implements FunCalcService {

    private final FunItemResp funItemResp;
    private final FunCalcResp funCalcResp;

    private final FunCareItemResp funCareItemResp;

    private final ResponseService responseService;

    //의전물품 저장
    public boolean saveFunCalc(List<FunItemInfo> funItemInfoList) {
        boolean saveYn = true;
        List<FunItemEntity> funItemEntityList = new ArrayList<>();

        for(int i=0; i<funItemInfoList.size(); i++) {

            funItemEntityList.add(FunItemEntity.builder().funItemInfo(funItemInfoList.get(i)).build());
        }
        System.out.println("saveFunCalc ====funItemInfo=====================" + funItemEntityList.toString());
        funItemResp.saveAll(funItemEntityList);

        return saveYn;
    }

    //의전물품 및 정산저장
    @Transactional
    public CommonResult saveFunItemCalc(FunItemCalcDto funItemCalcDto) {
        boolean saveYn = true;
        List<FunItemEntity> funItemEntityList = new ArrayList<>();
        List<FunItemInfo> funItemInfoList = funItemCalcDto.getFunItemList();
        FunCalcInfo funCalcInfo = funItemCalcDto.getFunCalcInfo();
        //FunCareItemInfo funCareItemInfo = funItemCalcDto.getFunCareItemInfo();
        /*
        FunCalcInfoM funCalcInfoM = funItemCalcDto.getFunCalcInfoM();
        FunCalcInfo funCalcInfo  = new FunCalcInfo();
        funCalcInfo.setAcceptAmt( );
        */
        FunItemId funItemId = null;

        for(int i=0; i<funItemInfoList.size(); i++) {
            funItemId = funItemInfoList.get(i).getFunItemId();

            if(!"".equals(funItemId.getAssiProdCd()) && funItemId.getAssiProdCd() != null) {
                funItemEntityList.add(FunItemEntity.builder().funItemInfo(funItemInfoList.get(i)).build());
            }
        }
        System.out.println("saveFunItemCalc.funItemEntityList=====================" + funItemEntityList.toString());
        System.out.println("saveFunItemCalc.funCalcInfo=====================" + funCalcInfo.toString());

        if(funItemId != null) {
            //의전물품 데이터 전체 삭제
            Integer delCnt = funItemResp.deleteByFunItemIdFunCtrlNo(funItemId.getFunCtrlNo());
            System.out.println("============> DelCnt = " + delCnt.toString());
        }

        //의전물품 데이터 저장
        funItemResp.saveAll(funItemEntityList);
        
        //의전결산 데이터 삭제
        if(funCalcResp.existsById(funCalcInfo.getFunCalcId())) {
            funCalcResp.deleteById(funCalcInfo.getFunCalcId());
        }

        //의전결산 데이터 저장
        funCalcResp.save(FunCalcEntity.builder().funCalcInfo(funCalcInfo).build());
/*
        //만기케어 물품 수정
        FunCareItemEntity funCareItemEntity = funCareItemResp.findByFunCtrlNoAndUseYn(funCalcInfo.getFunCalcId().getFunCtrlNo(), "Y");
        funCareItemEntity.setUseYn('N');

        //만기케어 물품 저장
        funCareItemResp.save(FunCareItemEntity.builder().funCareItemInfo(funCareItemInfo).build());
*/
        return responseService.getSuccessResult();
    }
}
