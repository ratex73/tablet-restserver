package kr.co.yedaham.tablet.restserver.restserver.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.yedaham.tablet.restserver.restserver.model.funmessage.FunMessageSendRequest;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;
import kr.co.yedaham.tablet.restserver.restserver.model.response.SingleResult;
import kr.co.yedaham.tablet.restserver.restserver.model.sms.FunResendRequest;
import kr.co.yedaham.tablet.restserver.restserver.model.sms.FunSmsEnd;
import kr.co.yedaham.tablet.restserver.restserver.model.sms.FunSmsStart;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import kr.co.yedaham.tablet.restserver.restserver.service.sms.FunSmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"11. FunSms"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SmsController {
    private final FunSmsService service;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "의전 시작 문자 조회", notes = "의전 시작 문자를 조회한다")
    @PostMapping(value = "/fun/smsstart")
    public CommonResult getFunSmsStart(@RequestParam String functrlno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(service.getFunSmsStart(functrlno));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "의전 종료 문자 조회", notes = "의전 종료 문자를 조회한다")
    @PostMapping(value = "/fun/smsend")
    public CommonResult getFunSmsEnd(@RequestParam String functrlno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(service.getFunSmsEnd(functrlno));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "서명 후 스크린샷 재발솧", notes = "서명 후 스크린샷을 재발송한다.")
    @PostMapping(value = "/fun/resend")
    public CommonResult funResend(@RequestBody FunResendRequest funResendRequest) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(service.getFunResend(funResendRequest));
    }

}
