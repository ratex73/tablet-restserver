package kr.co.yedaham.tablet.restserver.restserver.controller.v1;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.yedaham.tablet.restserver.restserver.config.security.JwtTokenProvider;
import kr.co.yedaham.tablet.restserver.restserver.model.funmessage.*;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import kr.co.yedaham.tablet.restserver.restserver.service.funMessage.FunMessageService;
import kr.co.yedaham.tablet.restserver.restserver.service.sms.TabletSmsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = {"17. FunMessage"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class FunMessageController {
    private static final Logger logger = LoggerFactory.getLogger(FunMessageController.class);
    private final FunMessageService funMessageService;
    private final ResponseService responseService;

    private final JwtTokenProvider jwtTokenProvider;

    private final TabletSmsService tabletSmsService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "부고문자 발솧", notes = "부고문자를 상주에게 발송한다.")
    @PostMapping(value = "/funMessage/send")
    public CommonResult funMessageSend(@RequestBody FunMessageSendRequest funMessageSendRequest) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(funMessageService.sendFunMessage(funMessageSendRequest));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "부고문자  조회", notes = "부고문자를 조회한다.")
    @PostMapping(value = "/funMessage/info")
    public CommonResult getFunMessageList(@RequestParam String funCtrlNo) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CommonResult funMessageInfo = funMessageService.getFunMessageInfo(funCtrlNo);
        return funMessageInfo;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "최종 개인정보활용 동의자 정보 조회", notes = "최종 개인정보활용 동의자 정보를 조회한다.")
    @GetMapping(value = "/funMessage/lastInfo")
    public CommonResult getLastFunMessageInfo(@RequestParam String funCtrlNo) throws Exception {
        System.out.println("###### Controller getLastFunMessageInfo funCtrlNo : " + funCtrlNo);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CommonResult funMessageInfo = funMessageService.getLastFunMessageInfo(funCtrlNo);
        return funMessageInfo;
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "부고문자 저장", notes = "부고문자를 저장한다")
    @PostMapping(value = "/funMessage/save")
    public CommonResult funMessageSave (@RequestBody List<FunMessageInfo> funMessageInfos) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(funMessageService.saveFunMessageInfo(funMessageInfos));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
      })
    @ApiOperation(value = "부고문자 상주들 저장", notes = "부고문자 상주들 저장한다")
    @PostMapping(value = "/funMessage/save/mours")
    public CommonResult funMessageSaveMours (@RequestBody List<FunMessageMours> funMessageMours) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(funMessageService.saveFunMessageMours(funMessageMours));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "부고문자 수정", notes = "부고문자를 수정한다")
    @PostMapping(value = "/funMessage/update")
    public CommonResult funMessageUpdate (@RequestBody FunMessageTotal funMessageTotal) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(funMessageService.updateFunMessageInfo(funMessageTotal));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "부고문자 예금주체크", notes = "부고문자 상주의 예금주를 체크한다.")
    @PostMapping(value = "/funMessage/accCheck")
    public CommonResult funMessageAccCheck (@RequestBody FunMessageAccCheckRequest funMessageAccCheckRequest) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(funMessageService.sendAccCheck(funMessageAccCheckRequest));
    }

    @ApiOperation(value = "부고 OTP 및 토큰 발급", notes = "부고 OTP 및 토큰을 발급한다.")
    @PostMapping(value = "/bugo/signin")
    public CommonResult bugoSignin(@RequestBody FunMessageSigninRequest funMessageSigninRequest) throws Exception {
        FunMessageSigninInfo funMessageSigninInfo = funMessageService.getFunMessageSigninInfo(funMessageSigninRequest);

        if(!ObjectUtils.isEmpty(funMessageSigninInfo)) {
            List<String> roles = new ArrayList<>();
            roles.add("ROLE_SUBCONTRACTOR");
            String createToken =  jwtTokenProvider.createToken(funMessageSigninInfo.getMfsUserId(),roles);
            funMessageSigninInfo.setToken(createToken);
            funMessageSigninInfo.setRole("ROLE_SUBCONTRACTOR");
            tabletSmsService.sendSmsMessage(funMessageSigninInfo.getCellPhoneNo(), funMessageSigninInfo.getOtp());
            return  responseService.getSingleResult(funMessageSigninInfo);
        }else {
            return  responseService.getFailResult(9999, "값이 존재하지 않습니다.");
        }

    }

     @ApiImplicitParams({
             @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
       })
    @ApiOperation(value = "부고문자 상주들 삭제", notes = "부고문자 상주들 삭제한다")
    @PostMapping(value = "/funMessage/delete/mours")
    public CommonResult funMessageDeleteMours (@RequestParam long seq, @RequestParam String funCtrlNo) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return funMessageService.deleteFunMessageMours(seq,funCtrlNo);
    }

     @ApiImplicitParams({
             @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
       })
    @ApiOperation(value = "부고문자 삭제", notes = "부고문자를 삭제한다")
    @PostMapping(value = "/funMessage/delete")
    public CommonResult funMessageDelete (@RequestBody FunMessageDeleteRequest funMessageDeleteRequest) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return funMessageService.deleteFunMessage(funMessageDeleteRequest);
    }

     @ApiImplicitParams({
             @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
     })
    @ApiOperation(value = "부고문자 상주들 수정", notes = "부고문자 상주들을 수정한다")
    @PostMapping(value = "/funMessage/update/mours")
    public CommonResult funMessageUpdateMours (@RequestBody List<FunMessageMours> funMessageMours) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(funMessageService.updateFunMessageMoursInfo(funMessageMours));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "부고답례장 발솧", notes = "부고답례장을 발송한다.")
    @PostMapping(value = "/funMessage/reply")
    public CommonResult funMessageReply(@RequestBody FunMessageSendRequest funMessageSendRequest) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(funMessageService.replyFunMessage(funMessageSendRequest));
    }
}
