package kr.co.yedaham.tablet.restserver.restserver.service.funMessage;

import kr.co.yedaham.tablet.restserver.restserver.model.funmessage.*;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;

import java.util.List;

public interface FunMessageService {
    public boolean sendFunMessage(FunMessageSendRequest funMessageSendRequest);
    public CommonResult getFunMessageInfo(String funCtrlNo);
    public List<FunMessageInfo> saveFunMessageInfo(List<FunMessageInfo> funMessageInfos);
    public List<Object> updateFunMessageInfo(FunMessageTotal funMessageTotal);
    public SendAccCheckReponse sendAccCheck(FunMessageAccCheckRequest funMessageAccCheckRequest);
    public List<FunMessageMours> saveFunMessageMours(List<FunMessageMours> funMessageMours);

    public FunMessageSigninInfo getFunMessageSigninInfo(FunMessageSigninRequest funMessageSigninRequest);

    public CommonResult deleteFunMessageMours(long seq, String funCtrlNo);

    public CommonResult deleteFunMessage(FunMessageDeleteRequest funMessageDeleteRequest);

    public List<FunMessageMours> updateFunMessageMoursInfo(List<FunMessageMours> funMessageMours);

    public boolean replyFunMessage(FunMessageSendRequest funMessageSendRequest);
}
