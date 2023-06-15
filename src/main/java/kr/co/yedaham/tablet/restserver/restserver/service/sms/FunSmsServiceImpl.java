package kr.co.yedaham.tablet.restserver.restserver.service.sms;

import kr.co.yedaham.tablet.restserver.restserver.model.response.SingleResult;
import kr.co.yedaham.tablet.restserver.restserver.model.sms.FunResendInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.sms.FunResendRequest;
import kr.co.yedaham.tablet.restserver.restserver.model.sms.FunSmsEnd;
import kr.co.yedaham.tablet.restserver.restserver.model.sms.FunSmsStart;
import kr.co.yedaham.tablet.restserver.restserver.resp.sms.SmsResp;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FunSmsServiceImpl implements FunSmsService{
    private final SmsResp smsResp;
    private final ResponseService responseService;

    private final TabletSmsService tabletSmsService;

    @Override
    public SingleResult<FunSmsStart> getFunSmsStart(String functrlno) {
        FunSmsStart funSmsStart = smsResp.findFunSmsStart(functrlno);

        return responseService.getSingleResult(funSmsStart);
    }

    @Override
    public SingleResult<FunSmsEnd> getFunSmsEnd(String funno) {
        FunSmsEnd funSmsEnd = smsResp.findFunSmsEnd(funno);
        return responseService.getSingleResult(funSmsEnd);
    }

    @Override
    public FunResendInfo getFunResend(FunResendRequest funResendRequest) {

        StringBuilder stringBuilder = new StringBuilder();
        for(int i =0; i< 6;i++) {
            int value = (int) ((Math.random() * 10000) % 10);
            stringBuilder.append(value);
        }
        String smsMsg = funResendRequest.getReason()+ "\n" +
                        "OTP : " + stringBuilder.toString();
        tabletSmsService.sendLmsMessage(funResendRequest.getEmpPhone(),smsMsg);

        FunResendInfo funResendInfo = new FunResendInfo();
        funResendInfo.setFunCtrlNo(funResendRequest.getFunCtrlNo());
        funResendInfo.setEmpNm(funResendRequest.getEmpNm());
        funResendInfo.setEmpPhone(funResendRequest.getEmpPhone());
        funResendInfo.setOtp(stringBuilder.toString());
        funResendInfo.setReason(funResendRequest.getReason());

        return funResendInfo;
    }
}
