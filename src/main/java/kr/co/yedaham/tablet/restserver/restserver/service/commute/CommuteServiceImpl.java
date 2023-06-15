package kr.co.yedaham.tablet.restserver.restserver.service.commute;


import kr.co.yedaham.tablet.restserver.restserver.entity.CommuteEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.commute.*;
import kr.co.yedaham.tablet.restserver.restserver.resp.commute.CommuteResp;
import kr.co.yedaham.tablet.restserver.restserver.resp.commute.CustomCommuteResp;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommuteServiceImpl implements  CommuteService{

    private final CustomCommuteResp customCommuteResp;
    private final CommuteResp commuteResp;
    private final ResponseService responseService;

    @Override
    public List<Commute> getCommuteList(CommuteRequest commuteRequest) {

         String sql = " SELECT \n" +
                "           A.SEQ,\n" +
                "           A.FUN_CTRL_NO,\n" +
                "           A.USER_ID,\n" +
                "           A.START_DATE,\n" +
                "           A.END_DATE,\n" +
                "           A.FUN_DAYS,\n" +
                "           NVL(A.START_LAT,0) START_LAT,\n" +
                "           NVL(A.START_LNG,0) START_LNG,\n" +
                "           NVL(A.END_LAT,0) END_LAT,\n" +
                "           NVL(A.END_LNG,0) END_LNG,\n" +
                "           A.START_TIME,\n" +
                "           A.END_TIME,\n" +
                "           NVL(B.EMPNM1,G.EMP_NAME) AS USER_NAME,\n" +
                "           D.FUN_NM AS FUN_NM,\n" +
                "           D.LAT AS LAT,\n" +
                "           D.LNG AS LNG,\n" +
                "           H.EMPNM1 AS FD_NAME\n" +
                "       FROM YDHRCH010M A LEFT JOIN HR001M B\n" +
                "                                ON A.USER_ID = B.EMPNO\n" +
                "                         LEFT JOIN TBFU1001 C\n" +
                "                                ON A.FUN_CTRL_NO = C.FUN_CTRL_NO\n" +
                "                         LEFT JOIN TBCM1005 D\n" +
                "                                ON D.FUN_CD = C.FUN_CD\n" +
                "                         LEFT JOIN HB001M E\n" +
                "                                ON C.MANAG_BRANCH_GROUP = E.DEPT\n" +
                "                         LEFT JOIN VW_BO_DEPT F\n" +
                "                                ON F.DEPT_CODE= C.MANAG_BRANCH_GROUP\n" +
                "                         LEFT JOIN TBFU4004 G\n" +
                "                                ON G.MFS_USERID = A.USER_ID\n" +
                "                         LEFT JOIN HR001M H\n" +
                "                                ON C.LEADER_ID = H.EMPNO\n" +
                "    WHERE B.RETDT IS NULL\n";

         if(commuteRequest.getRole().equals("ROLE_BRANCH_MANAGER")) {
             sql +=  " AND E.ADMIN_EMPNO = '" + commuteRequest.getUserId() + "'";
         }

         if(!commuteRequest.getFunCtrlNo().equals("")) {
             sql +=  " AND A.FUN_CTRL_NO = '" + commuteRequest.getFunCtrlNo() + "' ";
         }

        // if(!commuteRequest.getUserId().equals("")) {
       //     sql += " AND A.USER_ID = '" + commuteRequest.getUserId() + "' ";
        // }

         if(!commuteRequest.getFunNm().equals("")) {
             sql += " AND D.FUN_NM LIKE '%" + commuteRequest.getFunNm() + "%' ";
         }

         if(!commuteRequest.getFromDate().equals("") && !commuteRequest.getToDate().equals("")) {
             sql += " AND A.START_DATE BETWEEN '" + commuteRequest.getFromDate() + "' AND '" + commuteRequest.getToDate() + "'";
         }

         if(!commuteRequest.getDeptCodes().equals("")) {
            sql += "AND F.DEPT_CODE IN ( " + commuteRequest.getDeptCodes().toString().replace("[","").replace("]","") + " )";
         }

         if(!commuteRequest.getUserNm().equals("")) {
             sql += " AND (NVL(B.EMPNM1,G.EMP_NAME) = '" + commuteRequest.getUserNm() + "' OR H.EMPNM1 = '"+ commuteRequest.getUserNm()+"' )";
         }

         sql +=  "ORDER BY SEQ DESC";
        System.out.println(sql);
        List<Commute> commuteList = customCommuteResp.getCommuteList(sql);
        return commuteList;
    }

    @Override
    public CommuteSaveInfo commuteStartSaveInfo(CommuteSaveInfo saveInfo) {
         ModelMapper mapper = new ModelMapper();
        int commuteSeq = commuteResp.getNextValMySequence();
        saveInfo.setSeq(commuteSeq);

        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String nowDateString = now.format(date);
        saveInfo.setStartDate(nowDateString);

        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");
        String nowTimeString = now.format(time);
        saveInfo.setStartTime(nowTimeString);


        CommuteEntity save = commuteResp.save(CommuteEntity.builder().commuteSaveInfo(saveInfo).build());
        CommuteSaveInfo commuteSaveInfo = mapper.map(save, CommuteSaveInfo.class);
        System.out.println("변경된 값:  " + commuteSaveInfo);
        return commuteSaveInfo;
    }

    @Override
    public CommuteSaveInfo commuteEndSaveInfo(CommuteSaveInfo commuteSaveInfo) {
        ModelMapper mapper = new ModelMapper();
        CommuteSaveInfo commuteEndSaveInfo = new CommuteSaveInfo();
        Optional<CommuteEntity> beforeDate = commuteResp.findById(commuteSaveInfo.getSeq());
        System.out.println("기존 값 : " + beforeDate);
        if(beforeDate.isPresent()) {
            beforeDate.get().setEndLat(commuteSaveInfo.getEndLat());
            beforeDate.get().setEndLng(commuteSaveInfo.getEndLng());


            LocalDateTime now = LocalDateTime.now();

            DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String nowDateString = now.format(date);
            beforeDate.get().setEndDate(nowDateString);

            DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");
            String nowTimeString = now.format(time);
            beforeDate.get().setEndTime(nowTimeString);

            beforeDate.get().setEndDis(commuteSaveInfo.getEndDis());

            CommuteEntity updateSave = commuteResp.save(beforeDate.get());
            commuteEndSaveInfo = mapper.map(updateSave, CommuteSaveInfo.class);

        }
        return commuteEndSaveInfo;
    }

    @Override
    public List<DeptInfo> getDeptList(String userId) {

        List<DeptInfo> deptList = commuteResp.getDeptList(userId);
        return deptList;
    }

    @Override
    public List<CommuteCancelInfo> getCommuteCancelList(String userId) {
        List<CommuteCancelInfo> commuteCancelList = commuteResp.getCommuteCancelList(userId);
        return commuteCancelList;
    }

    @Override
    public List<CommuteFdInfo> getCommuteFdList(String funCtrlNo) {

        List<CommuteFdInfo> commuteFdList = commuteResp.getCommuteFdList(funCtrlNo);
        return commuteFdList;
    }

    @Override
    public ResponseService updateCommuteFunDays(long seq, String funDays) {

        try {
            Optional<CommuteEntity> beforeDate = commuteResp.findById(seq);
             if(beforeDate.isPresent()) {
                beforeDate.get().setFunDays(funDays);
                CommuteEntity updateSave = commuteResp.save(beforeDate.get());
                responseService.getSingleResult(updateSave);
            }

        }catch (Exception ex) {
            responseService.getFailResult(9999, ex.getMessage());
        }
        return responseService;
    }
}
