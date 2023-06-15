package kr.co.yedaham.tablet.restserver.restserver.service.funMessage;


import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.yedaham.tablet.restserver.restserver.config.restslack.SlackSenderManager;
import kr.co.yedaham.tablet.restserver.restserver.config.sms.SendProperties;
import kr.co.yedaham.tablet.restserver.restserver.config.sms.SmsProperties;
import kr.co.yedaham.tablet.restserver.restserver.entity.*;
import kr.co.yedaham.tablet.restserver.restserver.model.funmessage.*;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;
import kr.co.yedaham.tablet.restserver.restserver.model.slack.SlackTarget;
import kr.co.yedaham.tablet.restserver.restserver.resp.fun.FunResp;
import kr.co.yedaham.tablet.restserver.restserver.resp.funmessage.FunMessageHistoryResp;
import kr.co.yedaham.tablet.restserver.restserver.resp.funmessage.FunMessageMoursHistoryResp;
import kr.co.yedaham.tablet.restserver.restserver.resp.funmessage.FunMessageMoursResp;
import kr.co.yedaham.tablet.restserver.restserver.resp.funmessage.FunMessageResp;
import kr.co.yedaham.tablet.restserver.restserver.resp.sms.SendMmsEntityResp;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FunMessageServiceImpl implements FunMessageService{

    private static final Logger logger = LoggerFactory.getLogger(FunMessageServiceImpl.class);
    private final SlackSenderManager slackSenderManager;
    private final SendMmsEntityResp sendMmsEntityResp;
    private final FunMessageResp funMessageResp;
    private final FunMessageMoursResp funMessageMoursResp;

    private final FunResp funResp;

    private final SmsProperties smsProp;
    private final SendProperties sendProp;
    private final ResponseService responseService;

    private final FunMessageHistoryResp funMessageHistoryResp;
    private final FunMessageMoursHistoryResp funMessageMoursHistoryResp;



    private final RestTemplate restTemplate;
    @Value(value = "${hosturl.fsservice}")
    private String hostPath;

    @Override
    public boolean sendFunMessage(FunMessageSendRequest funMessageSendRequest) {

        boolean result = true;
        String sendMsgkey = smsProp.getMsgkey();
        String sendTelNo = smsProp.getTelNo();
        String memo = funMessageSendRequest.getMemo().replaceAll("\\\\n", "\n");
        logger.info("memo : " + memo);
        String smsMsg = "故 " + funMessageSendRequest.getDeadNm() + "님께서 별세 하셨기에 부고를 알려드립니다.\n" +
                        "상주 : " + funMessageSendRequest.getMourNm() + "\n" +
                        "장소 : " + funMessageSendRequest.getFunNm() + "\n" +
                        "▶故 " + funMessageSendRequest.getDeadNm() + "부고◀\n" +
                        sendProp.getFunMessageUrl() + "?seq="+funMessageSendRequest.getSeq()+"&fun_ctrl_no="+funMessageSendRequest.getFunCtrlNo() + "\n" +
                        memo;

        String[] splitPhone = funMessageSendRequest.getMournerPhone().split(",");

        for(String cellPhone : splitPhone) {

            if("T".equals(sendMsgkey)) {
                funMessageSendRequest.setMournerPhone(sendTelNo);
            }else {
                funMessageSendRequest.setMournerPhone(cellPhone);
            }

            sendMmsEntityResp.save(
                    SendMmsEntity.builder().
                            msgKey(sendMsgkey+sendMmsEntityResp.getNextValMySequence()).
                            calleeNo(funMessageSendRequest.getMournerPhone()).
                            callbackNo("15666644").
                            mmsMsg(smsMsg).
                            msgtype('L').
                            build());
        }
        ModelMapper mapper = new ModelMapper();
        LocalDateTime now = LocalDateTime.now();
        Optional<FunMessageEntity> beforeData = funMessageResp.findById(Long.valueOf(funMessageSendRequest.getSeq()));
        if(beforeData.isPresent()) {
            beforeData.get().setSendYn("Y");
            FunMessageEntity funMessageEntity = funMessageResp.save(beforeData.get());
            FunMessageInfo  funMessageInfo = mapper.map(funMessageEntity , FunMessageInfo.class);

            int historySeq = funMessageHistoryResp.getNextValMySequence();
            funMessageInfo.setHistoryNo(historySeq);
            funMessageInfo.setFlag("U");

            DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String nowDateTime = now.format(dateTime);
            LocalDateTime parseNowDateTime = LocalDateTime.parse(nowDateTime, dateTime);
            funMessageInfo.setHistoryDate(parseNowDateTime);
            FunMessageHistoryEntity funMessageHistoryEntity = FunMessageHistoryEntity.builder().funMessageInfo(funMessageInfo).build();
            funMessageHistoryResp.save(funMessageHistoryEntity);

        }

        return result;
    }

    @Override
    public CommonResult getFunMessageInfo(String funCtrlNo) {
        CommonResult commonResult;
        try {

            ModelMapper mapper = new ModelMapper();
            List<FunMessageInfos> funMessageInfoList  = funMessageResp.getFunMessageInfos(funCtrlNo);
            logger.info("funMessageInfoList : " + funMessageInfoList.toString());
            List<FunMessageMoursEntity> funMessageMoursEntityList = funMessageMoursResp.findByFunCtrlNo(funCtrlNo);
            if(funMessageInfoList.size() > 0) {
                List<FunMessageMours> funMessageMoursList = funMessageMoursEntityList.stream().sorted(Comparator.comparing(FunMessageMoursEntity::getFunSeq)).map(p -> mapper.map(p, FunMessageMours.class)).collect(Collectors.toList());
                return responseService.getListResult(Arrays.asList(funMessageInfoList, funMessageMoursList));
            }else {
                return responseService.getFailResult(9999, "값이 존재하지 않습니다.");
            }
        }catch (Exception e) {
            return  responseService.getFailResult(9999, e.getMessage());
        }

    }

    @Override
    public List<FunMessageInfo> saveFunMessageInfo(List<FunMessageInfo> saveInfo) {
        ModelMapper mapper = new ModelMapper();
        List<FunMessageEntity> funMessageEntityList = new ArrayList<>();
        List<FunMessageHistoryEntity> funMessageHistoryEntityList = new ArrayList<>();
        List<FunMessageInfo> result = new ArrayList<>();

        List<FunMessageInfo> chekcedFunMessageInfos = checkOverLapFunMessageInfoList(saveInfo);

        for(int i =0; i < chekcedFunMessageInfos.size(); i++) {
            int nextValMySequence = funMessageResp.getNextValMySequence();
            chekcedFunMessageInfos.get(i).setSeq(nextValMySequence);

            FunMessageEntity funMessageEntity = new FunMessageEntity();
            funMessageEntity.setSeq(nextValMySequence);
            funMessageResp.save(funMessageEntity);


            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String nowDateString = now.format(date);
            chekcedFunMessageInfos.get(i).setRegDate(nowDateString);

            DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
            String nowTimeString = now.format(time);
            chekcedFunMessageInfos.get(i).setRegTime(nowTimeString);



            funMessageEntityList.add(FunMessageEntity.builder().funMessageInfo(chekcedFunMessageInfos.get(i)).build());



            int historySeq = funMessageHistoryResp.getNextValMySequence();
            chekcedFunMessageInfos.get(i).setHistoryNo(historySeq);
            logger.info("historySeq : " + historySeq);

            FunMessageHistoryEntity funMessageHistoryEntity = new FunMessageHistoryEntity();
            funMessageHistoryEntity.setHistoryNo(historySeq);
            funMessageHistoryResp.save(funMessageHistoryEntity);

            chekcedFunMessageInfos.get(i).setFlag("C");

            DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String nowDateTime = now.format(dateTime);
            LocalDateTime parseNowDateTime = LocalDateTime.parse(nowDateTime, dateTime);
            chekcedFunMessageInfos.get(i).setHistoryDate(parseNowDateTime);


            funMessageHistoryEntityList.add(FunMessageHistoryEntity.builder().funMessageInfo(chekcedFunMessageInfos.get(i)).build());
        }

        if(chekcedFunMessageInfos.size() > 0) {
            List<FunMessageEntity> funMessageEntitys = funMessageResp.saveAll(funMessageEntityList);
            result = funMessageEntitys.stream().map(p -> mapper.map(p, FunMessageInfo.class)).collect(Collectors.toList());
            for(int i =0; i < chekcedFunMessageInfos.size(); i++) {
                result.get(i).setMourPhone(chekcedFunMessageInfos.get(i).getMourPhone());

            }
            funMessageHistoryResp.saveAll(funMessageHistoryEntityList);
        }
        return result;
    }


    public    List<FunMessageInfo> checkOverLapFunMessageInfoList(List<FunMessageInfo> saveInfo) {

        List<FunMessageInfo> funMessageInfoList = new ArrayList<>();
        funMessageInfoList.addAll(saveInfo);
        List<FunMessageMoursEntity> existFunMessageMoursList = funMessageMoursResp.findByFunCtrlNo(saveInfo.get(0).getFunCtrlNo());

        if(existFunMessageMoursList.size() > 0) {
            for(int i=0; i<saveInfo.size();i++) {
                for(int j=0;j<existFunMessageMoursList.size();j++) {
                    if( (saveInfo.get(i).getMourNm().equals(existFunMessageMoursList.get(j).getMourReatNm())) &&
                            (saveInfo.get(i).getMourPhone().equals(existFunMessageMoursList.get(j).getMourReatPhone()))
                    ) {
                        funMessageInfoList.remove(saveInfo.get(i));
                    }
                }
            }
        }

        logger.info("funMessageInfoList : " + funMessageInfoList);
        return funMessageInfoList;
    }

    @Override
    public List<Object> updateFunMessageInfo(FunMessageTotal funMessageTotal) {

        ModelMapper mapper = new ModelMapper();
        FunMessageInfo funMessageInfo = new FunMessageInfo();
        FunMessageMours funMessageMours = new FunMessageMours();
        Optional<FunMessageEntity> beforeData = funMessageResp.findById(funMessageTotal.getSeq());
        if(beforeData.isPresent()) {

            LocalDateTime now = LocalDateTime.now();

            DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String nowDateString = now.format(date);
            beforeData.get().setUpdateDate(nowDateString);

            DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
            String nowTimeString = now.format(time);
            beforeData.get().setUpdateTime(nowTimeString);
            if(!funMessageTotal.getReat().equals(beforeData.get().getReat())) {
                beforeData.get().setReat(funMessageTotal.getReat());
            }
            beforeData.get().setDeadNm(funMessageTotal.getDeadNm());
            beforeData.get().setCeme(funMessageTotal.getCeme());
            beforeData.get().setChuDuty(funMessageTotal.getChuDuty());
            beforeData.get().setSex(funMessageTotal.getSex());
            beforeData.get().setDeadRea(funMessageTotal.getDeadRea());
            beforeData.get().setDeadDate(funMessageTotal.getDeadDate());
            beforeData.get().setCaskDate(funMessageTotal.getCaskDate());
            beforeData.get().setCortDate(funMessageTotal.getCortDate());
            beforeData.get().setMemo(funMessageTotal.getMemo());
            beforeData.get().setMourAccState(funMessageTotal.getMourAccState());
            beforeData.get().setFunPlace(funMessageTotal.getFunPlace());
            beforeData.get().setFunTelNo(funMessageTotal.getFunTelNo());
            beforeData.get().setState(funMessageTotal.getState());
            beforeData.get().setCeme2(funMessageTotal.getCeme2());
            beforeData.get().setDeadAge(funMessageTotal.getDeadAge());
            FunMessageEntity funMessageEntity = funMessageResp.save(beforeData.get());
            funMessageInfo = mapper.map(funMessageEntity , FunMessageInfo.class);


            int historySeq = funMessageHistoryResp.getNextValMySequence();
            funMessageInfo.setHistoryNo(historySeq);
            funMessageInfo.setFlag("U");

            DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String nowDateTime = now.format(dateTime);
            LocalDateTime parseNowDateTime = LocalDateTime.parse(nowDateTime, dateTime);
            funMessageInfo.setHistoryDate(parseNowDateTime);
            FunMessageHistoryEntity funMessageHistoryEntity = FunMessageHistoryEntity.builder().funMessageInfo(funMessageInfo).build();
            funMessageHistoryResp.save(funMessageHistoryEntity);
        }

            Optional<FunMessageMoursEntity> beforeMoursData = funMessageMoursResp.findById(funMessageTotal.getFunseq());
            if(beforeMoursData.isPresent()) {

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String nowDateTime = now.format(dateTime);
                LocalDateTime parseNowDateTime = LocalDateTime.parse(nowDateTime, dateTime);

                beforeMoursData.get().setUpdateDate(parseNowDateTime);
                beforeMoursData.get().setUpdateId(funMessageTotal.getUpdateId());
                beforeMoursData.get().setMourReatNm(funMessageTotal.getMourReatNm());
                beforeMoursData.get().setMourReatAccNo(funMessageTotal.getMourReatAccNo());
                beforeMoursData.get().setMourReatBankNm(funMessageTotal.getMourReatBankNm());
                beforeMoursData.get().setMourReatPhone(funMessageTotal.getMourReatPhone());
                beforeMoursData.get().setMourAccJuminNo(funMessageTotal.getMourAccJuminNo());

                if(!funMessageTotal.getDeadReat().equals(beforeMoursData.get().getDeadReat())) {
                    beforeMoursData.get().setDeadReat(funMessageTotal.getDeadReat());
                    List<FunMessageMoursEntity> funCtrlNoAndDeadReatList = funMessageMoursResp.findByFunCtrlNoAndDeadReatAndFunSeq(funMessageTotal.getFunCtrlNo(),
                                                                                                funMessageTotal.getDeadReat(),funMessageTotal.getSeq());
                    beforeMoursData.get().setMourReatOrder(funCtrlNoAndDeadReatList.size()+1);
                }

                FunMessageMoursEntity save = funMessageMoursResp.save(beforeMoursData.get());
                funMessageMours  = mapper.map(save , FunMessageMours.class);


                int historySeq = funMessageMoursHistoryResp.getNextValMySequence();
                funMessageMours.setHistoryNo(historySeq);
                funMessageMours.setFlag("U");
                funMessageMours.setHistoryDate(parseNowDateTime);

                FunMessageMoursHistoryEntity funMessageMoursHistoryEntity = FunMessageMoursHistoryEntity.builder().funMessageMours((funMessageMours)).build();
                funMessageMoursHistoryResp.save(funMessageMoursHistoryEntity);

           
        }
        List<Object> objects = Arrays.asList(funMessageInfo, funMessageMours);
        return objects;
    }

    @Override
    public SendAccCheckReponse sendAccCheck(FunMessageAccCheckRequest funMessageAccCheckRequest) {
        SendAccCheckReponse sendAccCheckReponse = new SendAccCheckReponse();

        try {

             String url = hostPath + "/fs/jsp/callFStask_ch_ver_json.jsp?" +
                    "task=tklife.funeral.messagedispatch.task.RealTimeTelMsg&action=accountName&serviceID=telmsg.realtime.AccountName" +
                    "&params="+ getParamsAccCheck(funMessageAccCheckRequest);
            logger.info("getPymtInfo : "+url);
            ResponseEntity<String> response = restTemplate.postForEntity(URI.create(url), null, String.class);
            String data = response.getBody().trim();
            logger.info("sendAccCheck : "+data);
             ObjectMapper objectMapper = new ObjectMapper();
             sendAccCheckReponse = objectMapper.readValue(data, SendAccCheckReponse.class);


        }catch (Exception e) {
            logger.error("sendAccCheck : "+e.getMessage().toString());
            try {
                slackSenderManager.send(SlackTarget.CH_BOT,"sendAccCheck : "+e.getMessage().toString());
            }catch (Exception exception){
                 logger.error("sendAccCheck : slack error");
            }
        }

        return sendAccCheckReponse;
    }

    @Override
    public List<FunMessageMours> saveFunMessageMours(List<FunMessageMours> saveInfo) {

        ModelMapper mapper = new ModelMapper();
        List<FunMessageMoursEntity> funMessageMoursList = new ArrayList<>();
        List<FunMessageMoursHistoryEntity> funMessageMoursHistoryEntityList = new ArrayList<>();
        List<FunMessageMours> result = new ArrayList<>();

        List<FunMessageMours> chekcedFunMessageMours = checkOverLapFunMessageMoursList(saveInfo);

        for(int i=0; i<chekcedFunMessageMours.size();i++) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String nowDateTime = now.format(dateTime);
            LocalDateTime parseNowDateTime = LocalDateTime.parse(nowDateTime, dateTime);
            chekcedFunMessageMours.get(i).setRegDate(parseNowDateTime);


            int nextValMySequence = funMessageMoursResp.getNextValMySequence();
            chekcedFunMessageMours.get(i).setSeq(nextValMySequence);

            FunMessageMoursEntity funMessageMoursEntity = new FunMessageMoursEntity();
            funMessageMoursEntity.setSeq(nextValMySequence);
            funMessageMoursResp.save(funMessageMoursEntity);

            funMessageMoursList.add(FunMessageMoursEntity.builder().funMessageMours(chekcedFunMessageMours.get(i)).build());


            int historySeq = funMessageMoursHistoryResp.getNextValMySequence();
            chekcedFunMessageMours.get(i).setHistoryNo(historySeq);

            FunMessageMoursHistoryEntity funMessageMoursHistoryEntity = new FunMessageMoursHistoryEntity();
            funMessageMoursHistoryEntity.setHistoryNo(historySeq);
            funMessageMoursHistoryResp.save(funMessageMoursHistoryEntity);
            chekcedFunMessageMours.get(i).setFlag("C");
            chekcedFunMessageMours.get(i).setHistoryDate(parseNowDateTime);

            funMessageMoursHistoryEntityList.add(FunMessageMoursHistoryEntity.builder().funMessageMours(chekcedFunMessageMours.get(i)).build());


        }

        if(chekcedFunMessageMours.size() > 0) {
            List<FunMessageMoursEntity> funMessageMoursEntities = funMessageMoursResp.saveAll(funMessageMoursList);
            result = funMessageMoursEntities.stream().map(p -> mapper.map(p, FunMessageMours.class)).collect(Collectors.toList());

            funMessageMoursHistoryResp.saveAll(funMessageMoursHistoryEntityList);
        }
        return result;
    }

    public    List<FunMessageMours> checkOverLapFunMessageMoursList(List<FunMessageMours> saveInfo) {

        List<FunMessageMours> funMessageMoursList = new ArrayList<>();
        funMessageMoursList.addAll(saveInfo);

        List<FunMessageMoursEntity> existFunMessageMoursList = funMessageMoursResp.findByFunCtrlNo(saveInfo.get(0).getFunCtrlNo());

        if(existFunMessageMoursList.size() > 0) {
            for(int i=0; i<saveInfo.size();i++) {
                for(int j=0;j<existFunMessageMoursList.size();j++) {
                    if( (saveInfo.get(i).getMourReatNm().equals(existFunMessageMoursList.get(j).getMourReatNm())) &&
                            (saveInfo.get(i).getMourReatPhone().equals(existFunMessageMoursList.get(j).getMourReatPhone()))
                    ) {
                        funMessageMoursList.remove(saveInfo.get(i));
                    }
                }
            }
        }

        logger.info("funMessageMoursList : " + funMessageMoursList);
        return funMessageMoursList;
    }



    public String getParamsAccCheck(FunMessageAccCheckRequest funMessageAccCheckRequest) throws UnsupportedEncodingException {
         String params =
                "ACC_NO:"+funMessageAccCheckRequest.getAccNo()+","+
                "ACC_JUMIN_NO:"+ funMessageAccCheckRequest.getAccJuminNo() + "," +
                "InBankcd:"+funMessageAccCheckRequest.getInBankCd()+","+
                "REQ_CHANNEL:"+funMessageAccCheckRequest.getReqChanel()+","+
                "ACC_HOLD:" + URLEncoder.encode(funMessageAccCheckRequest.getAccHold(),"EUC-KR")+","+
                "ACC_TEL:"+funMessageAccCheckRequest.getAccTel()+","+
                "USER_ID:"+funMessageAccCheckRequest.getUserId();

         return params;

    }




    @Override
    public FunMessageSigninInfo getFunMessageSigninInfo(FunMessageSigninRequest funMessageSigninRequest) {
        FunMessageSigninInfo funFuneralFirstHelpInfo = funResp.getFunFuneralFirstHelpInfo(funMessageSigninRequest.getPhone());

        if(!ObjectUtils.isEmpty(funFuneralFirstHelpInfo)) {
            StringBuilder stringBuilder = new StringBuilder();
            for(int i =0; i< 6;i++) {
                int value = (int) ((Math.random() * 10000) % 10);
                stringBuilder.append(value);
            }
            funFuneralFirstHelpInfo.setOtp(stringBuilder.toString());
        }

        return funFuneralFirstHelpInfo;

    }

    @Override
    public CommonResult deleteFunMessageMours(long seq, String funCtrlNo) {

            ModelMapper mapper = new ModelMapper();

            try {
                FunMessageMoursEntity funMessageMoursEntity = funMessageMoursResp.findBySeqAndFunCtrlNo(seq,funCtrlNo);
                FunMessageMours funMessageMours = mapper.map(funMessageMoursEntity, FunMessageMours.class);

                int historySeq = funMessageMoursHistoryResp.getNextValMySequence();
                funMessageMours.setHistoryNo(historySeq);
                funMessageMours.setFlag("D");

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String nowDateTime = now.format(dateTime);
                LocalDateTime parseNowDateTime = LocalDateTime.parse(nowDateTime, dateTime);
                funMessageMours.setHistoryDate(parseNowDateTime);

                funMessageMoursHistoryResp.save(FunMessageMoursHistoryEntity.builder().funMessageMours(funMessageMours).build());
                funMessageMoursResp.deleteById(seq);

                return responseService.getSuccessResult();
            }catch (Exception e) {
                return responseService.getFailResult(9999,"값 삭제에 실패했습니다.");
            }

    }

    @Override
    public CommonResult deleteFunMessage(FunMessageDeleteRequest funMessageDeleteRequest) {

        ModelMapper mapper = new ModelMapper();

        List<FunMessageMours> funMessageMours = new ArrayList<>();
        List<FunMessageMoursHistoryEntity> funMessageMoursHistoryEntityList = new ArrayList<>();


        try {

            FunMessageEntity funMessageEntity = funMessageResp.findByFunCtrlNoAndSeq(funMessageDeleteRequest.getFunCtrlNo(),funMessageDeleteRequest.getSeq());
            FunMessageInfo funMessageInfo = mapper.map(funMessageEntity, FunMessageInfo.class);

            if(!ObjectUtils.isEmpty(funMessageInfo)) {
                int historySeq = funMessageHistoryResp.getNextValMySequence();
                funMessageInfo.setHistoryNo(historySeq);
                funMessageInfo.setFlag("D");


                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String nowDateTime = now.format(dateTime);
                LocalDateTime parseNowDateTime = LocalDateTime.parse(nowDateTime, dateTime);
                funMessageInfo.setHistoryDate(parseNowDateTime);

                funMessageHistoryResp.save(FunMessageHistoryEntity.builder().funMessageInfo(funMessageInfo).build());
                funMessageResp.deleteById(funMessageInfo.getSeq());
            }


            List<FunMessageMoursEntity> funMessageMoursEntityList = funMessageMoursResp.findByFunSeq(funMessageDeleteRequest.getSeq());
            funMessageMours = funMessageMoursEntityList.stream().map(p -> mapper.map(p, FunMessageMours.class)).collect(Collectors.toList());

            for(int j=0; j< funMessageMours.size();j++) {

                int historySeq = funMessageMoursHistoryResp.getNextValMySequence();
                funMessageMours.get(j).setHistoryNo(historySeq);

                FunMessageMoursHistoryEntity funMessageMoursHistoryEntity = new FunMessageMoursHistoryEntity();
                funMessageMoursHistoryEntity.setHistoryNo(historySeq);
                funMessageMoursHistoryResp.save(funMessageMoursHistoryEntity);

                funMessageMours.get(j).setFlag("D");

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String nowDateTime = now.format(dateTime);
                LocalDateTime parseNowDateTime = LocalDateTime.parse(nowDateTime, dateTime);
                funMessageMours.get(j).setHistoryDate(parseNowDateTime);

                funMessageMoursHistoryEntityList.add(FunMessageMoursHistoryEntity.builder().funMessageMours(funMessageMours.get(j)).build());
                funMessageMoursResp.deleteById(funMessageMours.get(j).getSeq());
            }

            if(funMessageMoursHistoryEntityList.size() > 0) {
                funMessageMoursHistoryResp.saveAll(funMessageMoursHistoryEntityList);
            }

            return responseService.getSuccessResult();

        }catch (Exception e) {
            return responseService.getFailResult(9999,"값 삭제에 실패했습니다.");
        }

    }

    @Override
    public List<FunMessageMours> updateFunMessageMoursInfo(List<FunMessageMours> funMessageMours) {
        ModelMapper mapper = new ModelMapper();
        List<FunMessageMours> funMessageMoursList = new ArrayList<>();
        List<FunMessageMoursHistoryEntity> funMessageMoursHistoryEntityList = new ArrayList<>();
            for(int i=0; i<funMessageMours.size();i++) {

                Optional<FunMessageMoursEntity> beforeData = funMessageMoursResp.findById(funMessageMours.get(i).getSeq());

                if(beforeData.isPresent()) {
                    beforeData.get().setDeadReat(funMessageMours.get(i).getDeadReat());
                    beforeData.get().setMourReatNm(funMessageMours.get(i).getMourReatNm());

                    FunMessageMoursEntity save = funMessageMoursResp.save(beforeData.get());
                    FunMessageMours funMessageInfoMours = mapper.map(save, FunMessageMours.class);
                    funMessageMoursList.add(funMessageInfoMours);

                    int historySeq = funMessageMoursHistoryResp.getNextValMySequence();
                    funMessageInfoMours.setHistoryNo(historySeq);

                    FunMessageMoursHistoryEntity funMessageMoursHistoryEntity = new FunMessageMoursHistoryEntity();
                    funMessageMoursHistoryEntity.setHistoryNo(historySeq);

                    funMessageInfoMours.setFlag("U");
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String nowDateTime = now.format(dateTime);
                    LocalDateTime parseNowDateTime = LocalDateTime.parse(nowDateTime, dateTime);
                    funMessageMoursHistoryEntity.setHistoryDate(parseNowDateTime);

                    funMessageMoursHistoryResp.save(funMessageMoursHistoryEntity);

                    funMessageMoursHistoryEntityList.add(FunMessageMoursHistoryEntity.builder().funMessageMours(funMessageInfoMours).build());
                }
                if(funMessageMoursHistoryEntityList.size() > 0) {
                    funMessageMoursHistoryResp.saveAll(funMessageMoursHistoryEntityList);
                }

            }
        return funMessageMoursList;
    }

    @Override
    public boolean replyFunMessage(FunMessageSendRequest funMessageSendRequest) {
        boolean result = true;
        String sendMsgkey = smsProp.getMsgkey();
        String sendTelNo = smsProp.getTelNo();

        String memo = funMessageSendRequest.getMemo().replaceAll("\\\\n", "\n");
        logger.info("memo : " + memo);

            if("T".equals(sendMsgkey)) {
                funMessageSendRequest.setMournerPhone(sendTelNo);
            }

            sendMmsEntityResp.save(
                    SendMmsEntity.builder().
                            msgKey(sendMsgkey+sendMmsEntityResp.getNextValMySequence()).
                            calleeNo(funMessageSendRequest.getMournerPhone()).
                            callbackNo("15666644").
                            mmsMsg(memo).
                            msgtype('L').
                            build());

        ModelMapper mapper = new ModelMapper();
        LocalDateTime now = LocalDateTime.now();
        Optional<FunMessageEntity> beforeData = funMessageResp.findById(Long.valueOf(funMessageSendRequest.getSeq()));

        if(beforeData.isPresent()) {
            beforeData.get().setReplyYn("Y");
            FunMessageEntity funMessageEntity = funMessageResp.save(beforeData.get());
            FunMessageInfo  funMessageInfo = mapper.map(funMessageEntity , FunMessageInfo.class);

            int historySeq = funMessageHistoryResp.getNextValMySequence();
            funMessageInfo.setHistoryNo(historySeq);
            funMessageInfo.setFlag("U");

            DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String nowDateTime = now.format(dateTime);
            LocalDateTime parseNowDateTime = LocalDateTime.parse(nowDateTime, dateTime);
            funMessageInfo.setHistoryDate(parseNowDateTime);
            FunMessageHistoryEntity funMessageHistoryEntity = FunMessageHistoryEntity.builder().funMessageInfo(funMessageInfo).build();
            funMessageHistoryResp.save(funMessageHistoryEntity);

        }


        return result;
    }


}
