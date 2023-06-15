package kr.co.yedaham.tablet.restserver.restserver.service.sms;

import kr.co.yedaham.tablet.restserver.restserver.model.response.SingleResult;
import kr.co.yedaham.tablet.restserver.restserver.model.sms.FunResendInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.sms.FunResendRequest;
import kr.co.yedaham.tablet.restserver.restserver.model.sms.FunSmsEnd;
import kr.co.yedaham.tablet.restserver.restserver.model.sms.FunSmsStart;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;

public interface FunSmsService {
    public SingleResult<FunSmsStart> getFunSmsStart(String functrlno);
    public SingleResult<FunSmsEnd> getFunSmsEnd(String functrlno);

    public FunResendInfo getFunResend(FunResendRequest funResendRequest);
}
