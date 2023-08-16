package kr.co.yedaham.tablet.restserver.restserver.service.sms;

import kr.co.yedaham.tablet.restserver.restserver.config.restslack.SlackSenderManager;
import kr.co.yedaham.tablet.restserver.restserver.config.sms.SmsProperties;
import kr.co.yedaham.tablet.restserver.restserver.entity.SendMmsEntity;
import kr.co.yedaham.tablet.restserver.restserver.entity.TabletSmsEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.fun.Subcontractor;
import kr.co.yedaham.tablet.restserver.restserver.model.slack.SlackTarget;
import kr.co.yedaham.tablet.restserver.restserver.model.sms.FunSmsPhone;
import kr.co.yedaham.tablet.restserver.restserver.resp.sms.SendMmsEntityResp;
import kr.co.yedaham.tablet.restserver.restserver.resp.sms.SmsResp;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TabletSmsServiceImpl implements TabletSmsService{

    private static final Logger logger = LoggerFactory.getLogger(TabletSmsServiceImpl.class);
    private final SmsResp smsResp;

    private final SlackSenderManager slackSenderManager;

    private final SendMmsEntityResp sendMmsEntityResp;

    @Autowired
    private SmsProperties smsProp;

    @Override
    public void smsInsert(String filename, String functrlno) {
        try {
            FunSmsPhone phone = smsResp.findFunSendSms(functrlno);
            TabletSmsEntity sms = new TabletSmsEntity();
            sms.setFilename(filename);
            sms.setCellPhone(phone.getCellPhone());
            sms.setSendYn('N');
            sms.setSendDate(LocalDate.now());
            smsResp.save(sms);
            smsResp.flush();
        } catch (Exception e) {
            slackSenderManager.send(SlackTarget.CH_BOT, "sms테스트" + e.getMessage());
        }
    }

    @Override
    public void smsInsert(String filename, String functrlno, String cellPhone) {
        try {
            TabletSmsEntity sms = new TabletSmsEntity();
            sms.setFilename(filename);
            sms.setCellPhone(cellPhone);
            sms.setSendYn('Y');
            sms.setSendDate(LocalDate.now());
            smsResp.save(sms);
            smsResp.flush();
        } catch (Exception e) {
            slackSenderManager.send(SlackTarget.CH_BOT, "sms테스트" + e.getMessage());
        }
    }

    @Override
    public void smsInsert(String filename, String functrlno, String cellPhone, String fileType) {
        try {

            logger.info("###### Start smsInsert #####");
            TabletSmsEntity sms = new TabletSmsEntity();
            sms.setFilename(filename);
            sms.setCellPhone(cellPhone);
            sms.setSendYn('Y');
            sms.setSendDate(LocalDate.now());
            sms.setFunCtrlNo(functrlno);
            sms.setFileType(fileType);

            if("".equals(fileType)) {
                fileType = "ITEM";
            }
            
            sms.setFileType(fileType);

            List<TabletSmsEntity> tabletSmsEntityList = smsResp.findByFunCtrlNoAndFileType(functrlno, fileType);

            if(tabletSmsEntityList.size() > 0) {
                for(int i=0; i<tabletSmsEntityList.size();i++) {
                    tabletSmsEntityList.get(i).setLastRegYn("N");
                }
            }
            sms.setLastRegYn("Y");
            smsResp.save(sms);
            smsResp.flush();
        } catch (Exception e) {
            slackSenderManager.send(SlackTarget.CH_BOT, "sms테스트" + e.getMessage());
        }
    }

    @Override
    public boolean sendSmsMessage(ArrayList<Subcontractor> subcontractors, String mmsMsg) {
        String sendMsgkey = smsProp.getMsgkey();
        String sendTelNo = smsProp.getTelNo();
        //String nextValEtc = sendMmsEntityResp.getNextValEtcSequence();

        for (Subcontractor subcontractor :subcontractors) {
            if("T".equals(sendMsgkey)) {

                subcontractor.setCellphone(sendTelNo);
            }
            sendMmsEntityResp.save(
                    SendMmsEntity.builder().
                            msgKey(sendMsgkey+sendMmsEntityResp.getNextValMySequence()).
                            calleeNo(subcontractor.getCellphone()).
                            callbackNo("15666644").
                            mmsMsg(mmsMsg).
                            msgtype('S').
                            etc1("Y").
                            build());
        }
        return true;
    }

    @Override
    public boolean sendLmsMessage(String cellPhone, String mmsMsg) {
        String sendMsgkey = smsProp.getMsgkey();
        String sendTelNo = smsProp.getTelNo();

        if("T".equals(sendMsgkey)) {
            cellPhone = sendTelNo;
        }

        sendMmsEntityResp.save(
                SendMmsEntity.builder().
                        msgKey(sendMsgkey+sendMmsEntityResp.getNextValMySequence()).
                        calleeNo(cellPhone).
                        callbackNo("15666644").
                        mmsMsg(mmsMsg).
                        msgtype('L').
                        build());
        return true;
    }

    @Override
    public boolean sendLmsMessage(String cellPhone, String mmsMsg, String fdPhone) {
        String sendMsgkey = smsProp.getMsgkey();
        String sendTelNo = smsProp.getTelNo();

        if("T".equals(sendMsgkey)) {
            cellPhone = sendTelNo;
        }

        sendMmsEntityResp.save(
                SendMmsEntity.builder().
                        msgKey(sendMsgkey+sendMmsEntityResp.getNextValMySequence()).
                        calleeNo(cellPhone).
                        callbackNo("15666644").
                        mmsMsg(mmsMsg).
                        msgtype('L').
                        build());
        return true;
    }

    @Override
    public boolean sendSmsMessage(String cellPhone, String mmsMsg) {
        String sendMsgkey = smsProp.getMsgkey();
        String sendTelNo = smsProp.getTelNo();

        if("T".equals(sendMsgkey)) {
            cellPhone = sendTelNo;
        }
           sendMmsEntityResp.save(
                            SendMmsEntity.builder().
                                    msgKey(sendMsgkey+sendMmsEntityResp.getNextValMySequence()).
                                    calleeNo(cellPhone).
                                    callbackNo("15666644").
                                    mmsMsg(mmsMsg).
                                    msgtype('S').
                                    build());
        return true;
    }

}
