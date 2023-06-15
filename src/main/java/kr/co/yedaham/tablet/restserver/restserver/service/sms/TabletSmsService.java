package kr.co.yedaham.tablet.restserver.restserver.service.sms;

import kr.co.yedaham.tablet.restserver.restserver.model.fun.Subcontractor;

import java.util.ArrayList;

public interface TabletSmsService {

    public void smsInsert(String filename, String functrlno, String cellPhone);

    public void smsInsert(String filename, String functrlno);

    public boolean sendSmsMessage(ArrayList<Subcontractor> subcontractors, String mmsMsg);

    public boolean sendLmsMessage(String cellPhone, String mmsMsg);

    public boolean sendLmsMessage(String cellPhone, String mmsMsg, String fdPhone);

    public boolean sendSmsMessage(String cellPhone, String mmsMsg);
}
