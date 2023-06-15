package kr.co.yedaham.tablet.restserver.restserver.service.fun;

import kr.co.yedaham.tablet.restserver.restserver.entity.FunEntity;
import kr.co.yedaham.tablet.restserver.restserver.entity.FunSigninEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.file.FileSiginRequest;
import kr.co.yedaham.tablet.restserver.restserver.model.fun.*;
import kr.co.yedaham.tablet.restserver.restserver.resp.fun.CustomResp;
import kr.co.yedaham.tablet.restserver.restserver.resp.fun.FunResp;
import kr.co.yedaham.tablet.restserver.restserver.resp.fun.FunSigininResp;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import kr.co.yedaham.tablet.restserver.restserver.service.sms.TabletSmsService;
import kr.co.yedaham.tablet.restserver.restserver.util.MaskingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FunServiceImpl implements FunService {
    private final FunResp funResp;
    private final CustomResp customResp;
    private final ResponseService responseService;
    private final TabletSmsService tabletSmsService;

    private final FunSigininResp funSigininResp;

    @Override
    public List<FunList> getFunList(FunPostRequest request) {
        String sql = " SELECT \n" +
                "                                 A.FUN_CTRL_NO AS FUNNO,\n" +
                "                                 TO_CHAR(A.REG_DATE,'YYYY-MM-DD') AS REGDATE,\n" +
                "                                 TO_CHAR(A.REG_DATE,'HH24:MI') AS REGTIME,\n" +
                "                                 A.CERT_NO AS CERTNO,\n" +
                "                                 (SELECT CD_NM FROM TBCM1012 WHERE CD = STATUS AND TYPE_CD='FUNSTATUS') AS STATE,\n" +
                "                                 D.FUN_NM AS FUNNM,\n" +
                "                                 (CASE WHEN B.PROD_CD='B10001' AND B.PROD_MAIN_CD ='1040001' THEN 'dusan'\n" +
                "                                 WHEN B.PROD_CD='C10001' AND B.PROD_MAIN_CD ='C140006' THEN 'choson'\n" +
                "                                 WHEN B.PROD_CD='C10001' AND B.PROD_MAIN_CD <>'C140006' THEN 'new'\n" +
                "                                 ELSE 'onetwo' END) AS PRODGB,\n" +
                "                                 B.PROD_CD AS PRODCD,\n" +
                "                                 (SELECT S_PROD_DNM FROM TBPR1001 WHERE PROD_MAIN_CD = B.PROD_MAIN_CD) AS PRODNM,\n" +
                "                                 C.CUST_ID AS CUSTID,\n" +
                "                                 REPLACE(C.CUST_NM, SUBSTR(C.CUST_NM,2,1),'*') AS CUSTNM,\n" +
                "                                 A.DEAD_NM AS DEADNM,\n" +
                "                                 REPLACE(A.MOURNER, SUBSTR(A.MOURNER,2,1),'*') AS MOURNM,\n" +
                "                                 E.EMPNM1 AS FDNAME,\n" +
                "                                 D.S_SUPP_COMP AS SUPP_COMP,\n" +
                "                                 D.S_SUPP_TEL1 AS SUPP_TEL1,\n" +
                "                                 D.S_SUPP_TEL2 AS SUPP_TEL2,\n" +
                "                                 nvl(D.LAT,0) AS LAT,\n" +
                "                                 nvl(D.LNG,0) AS LNG,\n" +
                "                                 REPLACE(E.ETCTELNO,'-','') AS FD_PHONE,\n" +
                "                                 NVL(H.ADD_DIS_AMT,0) AS ADDDISAMT,\n" +
                "                                 (SELECT HEAD_CELL_PHONE_NO FROM VW_BO_BRANCH WHERE HEAD_EMP_NO =A.PRES_ID) AS BRANCH_PHONE,\n" +
                "                                 NVL( (SELECT CELL_PHONE_NO FROM TBFU4004 WHERE EMP_NO = A.FIRST_HELP AND TBFU4004.USE_YN ='Y' AND TBFU4004.DEL_YN ='N')," +
                "                                      (SELECT CELL_PHONE_NO FROM TBFU4004 WHERE G.CORT_SUP_ID = EMP_NO AND TBFU4004.USE_YN ='Y' AND TBFU4004.DEL_YN ='N') " +
                "                                    ) AS MFS_USER_PHONE\n" +
                "                                 FROM TBFU1001 A \n" +
                "                                    LEFT JOIN TBNB1007 B \n" +
                "                                        ON A.CERT_NO = B.CERT_NO\n" +
                "                                    LEFT JOIN (SELECT CUST_ID, CUST_NM FROM TBCS1003 UNION ALL SELECT CUST_ID, CUST_NM FROM TBCS1002) C \n" +
                "                                        ON B.CUST_ID = C.CUST_ID\n" +
                "                                    LEFT JOIN TBCM1005 D \n" +
                "                                        ON A.FUN_CD = D.FUN_CD AND D.FUN_GB='A' AND D.USE_YN='Y'\n" +
                "                                    LEFT JOIN HR001M E ON A.LEADER_ID = E.EMPNO OR A.FIRST_FD =E.EMPNO\n" +
                "                                    LEFT outer join hb001m F on e.dept = F.dept\n" +
                "                                    LEFT JOIN TBFU4001 G ON A.FUN_CTRL_NO = G.FUN_CTRL_NO\n" +
                "                                    LEFT JOIN TBNB1081 H ON A.CERT_NO = H.CERT_NO\n" +
                "                                 WHERE TO_CHAR(A.REG_DATE,'yyyymmdd') BETWEEN '" + request.getFunStartDate() + "' AND '" + request.getFunEndDate() + "'\n";

        sql += "AND A.STATUS IN ( " + request.getStatus().toString().replace("[","").replace("]","") + " )";

        if(!request.getRole().equals("ROLE_ADMIN") && !request.getRole().equals("ROLE_CENTER_MANAGER")){
            if(!request.getRole().equals("ROLE_SUBCONTRACTOR")) {
                if (!request.getUserid().equals("")) {
                    sql += "and ( decode('" + request.getRole() + "','ROLE_USER',E.empno,decode('" + request.getRole() + "','ROLE_BRANCH_MANAGER',F.admin_empno)) = '" + request.getUserid() + "' OR  G.CORT_SUP_ID = '" + request.getUserid() + "' )";
                }
            }
        }

        if(request.getRole().equals("ROLE_SUBCONTRACTOR")) {
            sql += " AND A.FUN_CTRL_NO IN (\n" +
                    " SELECT FUN_CTRL_NO FROM TBFU1001 WHERE FIRST_HELP IN ( SELECT EMP_NO FROM TBFU4004 WHERE ADDR = '"+request.getUserid().substring(0, 7)+"')  \n" +
                    " UNION ALL \n" +
                    " SELECT FUN_CTRL_NO FROM TBFU4001 WHERE CORT_SUP_ID IN ( SELECT EMP_NO FROM TBFU4004 WHERE ADDR = '"+request.getUserid().substring(0, 7)+"') \n" +
                    ")" ;
        }

        if(!request.getFunnm().equals("")) {
            sql += " AND NVL(D.FUN_NM,'') LIKE '" + request.getFunnm() + "%' ";
        }

        if(!request.getFunno().equals("")) {
            sql += " AND A.FUN_CTRL_NO = '" + request.getFunno() + "' ";
        }

        if(!request.getDeadNm().equals("")) {
            sql += " AND A.DEAD_NM = '" + request.getDeadNm() + "' ";
        }

        if(!request.getCustNm().equals("")) {
            sql += " AND C.CUST_NM = '" + request.getCustNm() + "' ";
        }

        if(!request.getFdNm().equals("")) {
            sql += " AND E.EMPNM1 = '" + request.getFdNm() + "' ";
        }

        sql += " ORDER BY A.FUN_CTRL_NO DESC";
        List<FunList> funList = customResp.getFunList(sql);
        return funList;
    }

    @Override
    public ArrayList<Subcontractor> getSubcontractPhoneList(String userid) {
        return funResp.getSubcontractPhoneList(userid);
    }

    @Override
    public ArrayList<SubcontractorFunList> getSubcontractFunList(String userid) {
        ArrayList<SubcontractorFunList> subcontractFunList = funResp.getSubcontractFunList(userid);
        return subcontractFunList;
    }

    @Override
    public FunCarInfo findGetFunCarInfo(String functrlno) {
        return funResp.findGetFunCarInfo(functrlno);
    }

    @Override
    public FunProgressInfo findFunProgressInfo(String functrlno) {
        return funResp.findFunProgressInfo(functrlno);
    }

    @Override
    public FunCustomerInfo findFunCustomerInfo(String functrlno) {
        return funResp.findFunCustomerInfo(functrlno);
    }

    @Override
    public FunFuneralInfo1 findFuneralInfo1(String functrlno) {
        return funResp.findFunFuneralInfo1(functrlno);
    }

    @Override
    public FunFuneralInfo2 findFuneralInfo2(String functrlno) {
        return funResp.findFunFuneralInfo2(functrlno);
    }

    public FunWorkerInfo findFunWorkerInfo(String functrlno) {
        return  funResp.findFunWorkerInfo(functrlno);
    }

    @Override
    public ResponseService sendFunSms(FunSmsInfo funSmsInfo) {
        ArrayList<FunSmsEmpList> funSmsEmpList = null;
        String sSmsType = funSmsInfo.getSmsType();
        String sFunCtrlNo = funSmsInfo.getFunno();

        if("S".equals(sSmsType) ) {
            funSmsEmpList = funResp.getFunStartSmsEmpList(sFunCtrlNo);
        } else if("E".equals(sSmsType) ) {
            funSmsEmpList = funResp.getFunEndSmsEmpList(sFunCtrlNo);
        }

        String sendTelNo = "";

        for (FunSmsEmpList empList:funSmsEmpList) {
            sendTelNo = empList.getTelNo();
            tabletSmsService.sendLmsMessage(empList.getTelNo(), funSmsInfo.getSmsContents(),funSmsInfo.getFdPhone());
        }
        return responseService;
    }

    @Override
    public ArrayList<FunSmsEmpList> getFunSmsEmpList(String functrlno, String smsType) {
        ArrayList<FunSmsEmpList> funSmsEmpList = null;
        //smsType: S(시작문자), E(종료문자)
        if ("S".equals(smsType)) {
            funSmsEmpList = funResp.getFunStartSmsEmpList(functrlno);
        } else if ("E".equals(smsType)) {
            funSmsEmpList = funResp.getFunEndSmsEmpList(functrlno);
        }
        return funSmsEmpList;
    }

    @Override
    public ResponseService updateDeparted(DepartedInfo departedInfo) {
        try {
            Optional<FunEntity> beforeData = funResp.findById(departedInfo.getFunno());
            if (beforeData.isPresent()) {
                beforeData.get().setDeadNm(departedInfo.getDeadNm());
                beforeData.get().setSex(departedInfo.getSex());
                beforeData.get().setAge(departedInfo.getAge());
                beforeData.get().setReat(departedInfo.getReat());
                beforeData.get().setClan(departedInfo.getClan());
                beforeData.get().setDeadRea(departedInfo.getDeadRea());
                beforeData.get().setReligion(departedInfo.getReligion());
                beforeData.get().setBaptismNm(departedInfo.getBaptismNm());
                beforeData.get().setChuDuty(departedInfo.getChuDuty());
                beforeData.get().setDeadHeig(departedInfo.getDeadHeig());
                beforeData.get().setDeadWeig(departedInfo.getDeadWeig());
                beforeData.get().setCasketSize(departedInfo.getCasketSize());
                beforeData.get().setMourner(departedInfo.getMourner());
                beforeData.get().setBereNum1(departedInfo.getBereNum1());
                beforeData.get().setBereNum2(departedInfo.getBereNum2());
                beforeData.get().setBereNum3(departedInfo.getBereNum3());
                beforeData.get().setBereNum4(departedInfo.getBereNum4());
                beforeData.get().setBereNum5(departedInfo.getBereNum5());
                beforeData.get().setBereNum6(departedInfo.getBereNum6());
                beforeData.get().setBereNum7(departedInfo.getBereNum7());
                beforeData.get().setBereNum8(departedInfo.getBereNum8());
                FunEntity save = funResp.save(beforeData.get());
                responseService.getSingleResult(save);
            }
        } catch (Exception ex) {
            responseService.getFailResult(9999, ex.getMessage());
        }
        return responseService;
    }

    @Override
    public ResponseService updateAmbulanceInfo(AmbulanceInfo ambulanceInfo) {
        try {
            Optional<FunEntity> beforeData = funResp.findById(ambulanceInfo.getFunno());
            if (beforeData.isPresent()) {
                beforeData.get().setFunCd(ambulanceInfo.getFunCd());
                beforeData.get().setFunResGb(ambulanceInfo.getFunResGb());
                beforeData.get().setAmbuBizCd(ambulanceInfo.getAmbuBizCd());
                beforeData.get().setAmbuUseYn(ambulanceInfo.getAmbuUseYn());
                beforeData.get().setAmbReasonCode(ambulanceInfo.getAmbReasonCode());
                beforeData.get().setAmbuCostUseYn(ambulanceInfo.getAmbuCostUseYn());
                beforeData.get().setAmbAmt(ambulanceInfo.getAmbAmt());
                beforeData.get().setAmbAmtType(ambulanceInfo.getAmbAmtType());
                FunEntity save = funResp.save(beforeData.get());
                responseService.getSingleResult(save);
            }
        } catch (Exception ex) {
            responseService.getFailResult(9999, ex.getMessage());
        }
        return responseService;
    }

    @Override
    public ResponseService updateBurialInfo(BurialInfo burialInfo) {
        try {
            Optional<FunEntity> beforeData = funResp.findById(burialInfo.getFunno());
            if (beforeData.isPresent()) {
                beforeData.get().setFunMeth(burialInfo.getFunMeth());
                beforeData.get().setBurialMeth(burialInfo.getBurialMeth());
                beforeData.get().setFunAbre(burialInfo.getFunAbre());
                beforeData.get().setCremaBizCd(burialInfo.getCremaBizCd());
                beforeData.get().setReserDate(burialInfo.getReserDate());
                beforeData.get().setReserTime(burialInfo.getReserTime());
                beforeData.get().setReserMin(burialInfo.getReserMin());
                beforeData.get().setRevYn(burialInfo.getRevYn());
                beforeData.get().setEnshMeth(burialInfo.getEnshMeth());
                beforeData.get().setConstBizCd(burialInfo.getConstBizCd());
                beforeData.get().setCemeZipCode(burialInfo.getCemeZipCode());
                beforeData.get().setCemeAddr1(burialInfo.getCemeAddr1());
                beforeData.get().setCemeAddr2(burialInfo.getCemeAddr2());
                beforeData.get().setCemeType(burialInfo.getCemeType());
                FunEntity save = funResp.save(beforeData.get());
                responseService.getSingleResult(save);
            }
        } catch (Exception ex) {
            responseService.getFailResult(9999, ex.getMessage());
        }
        return responseService;
    }

    @Override
    public ResponseService updateFunCarlInfo(FunCarUpdateInfo funCarUpdateInfo) {
        try {
            Optional<FunEntity> beforeData = funResp.findById(funCarUpdateInfo.getFunno());
            if (beforeData.isPresent()) {
                beforeData.get().setLimUseYn(funCarUpdateInfo.getLimUseYn());
                beforeData.get().setBusUseYn(funCarUpdateInfo.getBusUseYn());
                FunEntity save = funResp.save(beforeData.get());
                responseService.getSingleResult(save);
            }
        } catch (Exception ex) {
            responseService.getFailResult(9999, ex.getMessage());
        }
        return responseService;
    }

    @Override
    public boolean fileSignin(FileSiginRequest fileSiginRequest) {

        boolean result = true;
        fileSiginRequest.getFileSignin().setFileType("2");
        fileSiginRequest.getFileSignin().setFileSeq("1");

        Optional<FunSigninEntity> data = funSigininResp.findById(fileSiginRequest.getFileSignin());
        if(data.isPresent()) {
            funSigininResp.deleteById(fileSiginRequest.getFileSignin());
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowDateTime = now.format(dateTime);
        LocalDateTime parseNowDateTime = LocalDateTime.parse(nowDateTime, dateTime);
        fileSiginRequest.setRegDate(parseNowDateTime);

        fileSiginRequest.setMourner("1");
        fileSiginRequest.setFileSrc("T");


        FunSigninEntity funSigninEntity = FunSigninEntity.builder().fileSiginRequest(fileSiginRequest).build();
        funSigininResp.save(funSigninEntity);
        return result;
    }
}
