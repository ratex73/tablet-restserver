package kr.co.yedaham.tablet.restserver.restserver.service.funCalc;

import kr.co.yedaham.tablet.restserver.restserver.entity.FunCalcEntity;
import kr.co.yedaham.tablet.restserver.restserver.entity.FunCareItemEntity;
import kr.co.yedaham.tablet.restserver.restserver.entity.FunItemEntity;
import kr.co.yedaham.tablet.restserver.restserver.entity.FunMessageMoursEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.*;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;
import kr.co.yedaham.tablet.restserver.restserver.resp.funCalc.FunCalcResp;
import kr.co.yedaham.tablet.restserver.restserver.resp.funCalc.FunCareItemResp;
import kr.co.yedaham.tablet.restserver.restserver.resp.funCalc.FunItemResp;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FunCalcServiceImpl implements FunCalcService {

    private static final Logger logger = LoggerFactory.getLogger(FunCalcServiceImpl.class);
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
        logger.info("saveFunCalc ====funItemInfo=====================" + funItemEntityList.toString());
        funItemResp.saveAll(funItemEntityList);

        return saveYn;
    }

    //의전물품 및 정산저장
    @Transactional
    public CommonResult saveFunItemCalc(FunItemCalcDto funItemCalcDto) {
        
        logger.info("######################### saveFunItemCalc Result Start #########################");

        boolean saveYn = true;
        List<FunItemEntity> funItemEntityList = new ArrayList<>();
        List<FunItemInfo> funItemInfoList = funItemCalcDto.getFunItemList();
        FunCalcInfo funCalcInfo = funItemCalcDto.getFunCalcInfo();
        FunCareItemInfo funCareItemInfo = funItemCalcDto.getFunCareItemInfo();
        String prodGb = funItemCalcDto.getProdgb(); //new:신상품, onetwo:예다함1,2호, dusan : 두산, optional :자율선택형

        FunItemId funItemId = null;
        Integer funItemQty = null;
        boolean addYn = true;

        for(int i=0; i<funItemInfoList.size(); i++) {
            addYn = true;
            funItemId = funItemInfoList.get(i).getFunItemId();
            funItemQty = funItemInfoList.get(i).getQty();

            //신상품인 경우
            if( (prodGb == null || !"new".equals(prodGb)) ) {
                funItemInfoList.get(i).setState("");
            }

            //예다함 1,2호가 아닌 경우 물품 전체 페이백 물품은 TBFU1004에 저장하지 않음
            if( (prodGb == null || !"onetwo".equals(prodGb)) && funItemInfoList.get(i).getQty() == 0) {
                addYn = false;
                funItemInfoList.get(i).setState("");
            }

            if("99".equals(funItemId.getMainGb()) ) {
                addYn = false;
            }

            if("onetwo".equals(prodGb)) { //예다함 1,2호의 경우 페이백 물품(수량이 0으로 셋팅된 물품)은 TBFU1004에 저장하고 STATE='2'로 셋팅
                if(funItemInfoList.get(i).getQty() == 0) {
                    funItemInfoList.get(i).setState("2"); //페이백 처리
                }
            }

            if (addYn) {
                funItemEntityList.add(FunItemEntity.builder().funItemInfo(funItemInfoList.get(i)).build());
            }
        }
        logger.info("saveFunItemCalc.funItemEntityList=====================" + funItemEntityList.toString());
        logger.info("saveFunItemCalc.funCalcInfo=====================" + funCalcInfo.toString());

        if(funItemId != null) {
            //의전물품 데이터 전체 삭제
            Integer delCnt = funItemResp.deleteByFunItemIdFunCtrlNo(funItemId.getFunCtrlNo());
            logger.info("============> DelCnt = " + delCnt.toString());
        }

        //의전물품 데이터 저장
        funItemResp.saveAll(funItemEntityList);

        Integer custAmt = 0;
        custAmt = funCalcInfo.getSProdAmt() - funCalcInfo.getDisAmt() + funCalcInfo.getAddMinAmt() - funCalcInfo.getPymtAmt();

        logger.info("============> custAmt = " + custAmt);

        Optional<FunCalcEntity> beforeFunCalcData = funCalcResp.findById(funCalcInfo.getFunCalcId());

        if (beforeFunCalcData.isPresent()) {
            beforeFunCalcData.get().setSProdAmt(funCalcInfo.getSProdAmt());
            beforeFunCalcData.get().setDisAmt(funCalcInfo.getDisAmt());
            beforeFunCalcData.get().setAddMinAmt(funCalcInfo.getAddMinAmt());
            beforeFunCalcData.get().setPymtAmt(funCalcInfo.getPymtAmt());
            beforeFunCalcData.get().setCustAmt(custAmt);
        }
        else {
            funCalcInfo.setCustAmt(custAmt);
            //의전결산 데이터 저장
            funCalcResp.save(FunCalcEntity.builder().funCalcInfo(funCalcInfo).build());
        }
        /*
        //의전결산 데이터 삭제
        if(funCalcResp.existsById(funCalcInfo.getFunCalcId())) {
            funCalcResp.deleteById(funCalcInfo.getFunCalcId());
        }
         */

        //만기케어 물품 수정
        FunCareItemEntity funCareItemEntity = funCareItemResp.findByFunCtrlNoAndUseYn(funCalcInfo.getFunCalcId().getFunCtrlNo(), 'Y');

        if (funCareItemEntity != null) {
            funCareItemEntity.setUseYn('N');
            funCareItemEntity.setUpdateDate(LocalDateTime.now());
        }

        if(!"".equals(funCareItemInfo.getFunCtrlNo()) && funCareItemInfo.getFunCtrlNo() != null) {
            logger.info("funCareItem Save : " + funCareItemInfo.toString());
            //만기케어 물품 저장
            funCareItemResp.save(FunCareItemEntity.builder().funCareItemInfo(funCareItemInfo).build());
        }
        else {
            logger.info("funCareItem Save : 저장 대상 건 없음");
        }
        logger.info("######################### saveFunItemCalc Result End #########################");

        return responseService.getSuccessResult();
    }
}
