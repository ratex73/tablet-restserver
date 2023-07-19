package kr.co.yedaham.tablet.restserver.restserver.service.funCalc;

import kr.co.yedaham.tablet.restserver.restserver.entity.FunCalcEntity;
import kr.co.yedaham.tablet.restserver.restserver.entity.FunCareItemEntity;
import kr.co.yedaham.tablet.restserver.restserver.entity.FunItemEntity;
import kr.co.yedaham.tablet.restserver.restserver.entity.FunMessageMoursEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.contractamt.ContractAmt;
import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.*;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;
import kr.co.yedaham.tablet.restserver.restserver.resp.funCalc.FunCalcResp;
import kr.co.yedaham.tablet.restserver.restserver.resp.funCalc.FunCareItemResp;
import kr.co.yedaham.tablet.restserver.restserver.resp.funCalc.FunItemResp;
import kr.co.yedaham.tablet.restserver.restserver.resp.contractamt.ContractAmtResp;
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

    private final ContractAmtResp contractAmtResp;
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
        FunItemInfo funItemInfo;

        for(int i=0; i<funItemInfoList.size(); i++) {
            addYn = true;
            funItemId = funItemInfoList.get(i).getFunItemId();
            funItemInfo = funItemInfoList.get(i);

            //신상품인 경우
            if( prodGb == null || "new".equals(prodGb) ) {
                funItemInfo.setState("");
            }
            
            //물품코드가 null 인 경우 저장하지 않음
            if("".equals(funItemId.getAssiProdCd()) || funItemId.getAssiProdCd() == null) {
                addYn = false;
            }

            //예다함 1,2호가 아닌 경우 물품 전체 페이백 물품은 TBFU1004에 저장하지 않음
            if( "dusan".equals(prodGb) && funItemInfo.getQty() == -1) { //두산의 경우 미선택 물품은 F/E에서 -1로 셋팅해서 보내줌(남녀 상복의 수량이 기본 0이기 때문)
                addYn = false;
            }
            //예다함 1,2호가 아닌 경우 물품 전체 페이백 물품은 TBFU1004에 저장하지 않음
            else if( (prodGb == null || (!"onetwo".equals(prodGb) && !"dusan".equals(prodGb))) && funItemInfo.getQty() == 0) {
                addYn = false;
                funItemInfo.setState("");
            }

            //입력되는 데이터가 만기케어인 경우 추가 안함
            if("99".equals(funItemId.getMainGb()) ) {
                addYn = false;
            }

            if("onetwo".equals(prodGb)) { //예다함 1,2호의 경우 페이백 물품(수량이 0으로 셋팅된 물품)은 TBFU1004에 저장하고 STATE='2'로 셋팅
                if(funItemInfo.getQty() == 0) {
                    funItemInfoList.get(i).setState("2"); //페이백 처리
                }
            }

            if("dusan".equals(prodGb)) { //두산 상품인 경우
                funItemInfoList.get(i).setCreatYn("N");
                if(funItemInfoList.get(i).getAmt() == 0) {
                    funItemInfoList.get(i).setAmt(null);
                }
            }

            funItemInfoList.get(i).setUpdateDate(LocalDateTime.now());

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

        Integer sProdAmt = funCalcInfo.getSProdAmt();
        Integer custAmt = 0;

        //두산 상품인 경우 신상품인 경우
        if("dusan".equals(prodGb)) {
            /*
            ContractAmt contractAmt = contractAmtResp.findContractAmt(funItemCalcDto.getCertno(), funItemId.getFunCtrlNo());
            sProdAmt = contractAmt.getSProdAmt();

            logger.info("============> ContractAmt = " + contractAmt.toString());

            if(sProdAmt == 3060000) {
                custAmt = funCalcInfo.getAddMinAmt();
            }
            else {
                custAmt = sProdAmt + funCalcInfo.getAddMinAmt();
            }
            */
            funItemCalcDto.getFunCalcInfo().setSProdAmt(sProdAmt);
            funItemCalcDto.getFunCalcInfo().setCustAmt(custAmt);
            funItemCalcDto.getFunCalcInfo().setRemaAmt(sProdAmt);
            //funItemCalcDto.getFunCalcInfo().setTotAmt(sProdAmt + custAmt);
            //funItemCalcDto.getFunCalcInfo().setBalAmt(sProdAmt + custAmt);

        }
        else {

            Integer excpAmt = 0;
            //예다함 1,2호의 경우 초기감면금액 계산해줘야 함
            if("onetwo".equals(prodGb)) {
                ContractAmt contractAmt = contractAmtResp.findContractAmt(funItemCalcDto.getCertno(), funItemId.getFunCtrlNo());
                excpAmt = contractAmt.getExcpAmt();
            }

            custAmt = funCalcInfo.getSProdAmt() - funCalcInfo.getDisAmt() - excpAmt + funCalcInfo.getAddMinAmt() - funCalcInfo.getPymtAmt();
        }


        logger.info("============> custAmt = " + custAmt);

        Optional<FunCalcEntity> beforeFunCalcData = funCalcResp.findById(funCalcInfo.getFunCalcId());

        if (beforeFunCalcData.isPresent()) {
            funCalcResp.deleteById(funCalcInfo.getFunCalcId());
            /*
            beforeFunCalcData.get().setSProdAmt(sProdAmt);
            beforeFunCalcData.get().setDisAmt(funCalcInfo.getDisAmt());
            beforeFunCalcData.get().setAddMinAmt(funCalcInfo.getAddMinAmt());
            beforeFunCalcData.get().setPymtAmt(funCalcInfo.getPymtAmt());
            beforeFunCalcData.get().setCustAmt(custAmt);
             */
        }

        funCalcInfo.setCustAmt(custAmt);
        //의전결산 데이터 저장
        funCalcResp.save(FunCalcEntity.builder().funCalcInfo(funCalcInfo).build());

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
            funCareItemInfo.setUpdateDate(LocalDateTime.now());
            funCareItemResp.save(FunCareItemEntity.builder().funCareItemInfo(funCareItemInfo).build());
        }
        else {
            logger.info("funCareItem Save : 저장 대상 건 없음");
        }
        logger.info("######################### saveFunItemCalc Result End #########################");

        return responseService.getSuccessResult();
    }
}
