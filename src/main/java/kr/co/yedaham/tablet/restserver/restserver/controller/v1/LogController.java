package kr.co.yedaham.tablet.restserver.restserver.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.yedaham.tablet.restserver.restserver.advice.exception.CUseridSigninFailedException;
import kr.co.yedaham.tablet.restserver.restserver.config.restslack.SlackSenderManager;
import kr.co.yedaham.tablet.restserver.restserver.service.log.LogServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static kr.co.yedaham.tablet.restserver.restserver.model.slack.SlackTarget.CH_BOT;

@Api(tags = {"13. Log"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class LogController {
    private static final Logger logger = LoggerFactory.getLogger(LogController.class);
    private final SlackSenderManager slackSenderManager;
    private final LogServiceImpl logService;

    @ApiOperation(value = "슬랙 로그를 저장", notes = "슬랙 로그를 Z저장한다")
    @PostMapping(value = "/slack")
    public String sendSlackMessage (@RequestBody String message, @RequestHeader String DeviceType) {
        if(DeviceType != null && !(DeviceType.equals("Galaxy Tab S5e") || DeviceType.equals("SM-T720") || DeviceType.equals("SM-T725N")) ) {
            slackSenderManager.send(CH_BOT, DeviceType+":비인가 디바이스로 슬랙로그에 접속하였습니다." + message);
            throw new CUseridSigninFailedException();
        }

        return logService.sendSlackServer(message);
    }

    @ApiOperation(value = "로그 서버에 로그 저장", notes = "로그 서버에 로그 저장한다")
    @PostMapping(value = "/log")
    public String sendLogMessage (@RequestBody String message, @RequestHeader String DeviceType) {
        if(DeviceType != null && !(DeviceType.equals("Galaxy Tab S5e") || DeviceType.equals("SM-T505N") || DeviceType.equals("SM-T720") || DeviceType.equals("SM-T725N") || DeviceType.equals("SM-T500") || DeviceType.equals("SM-T875N") || DeviceType.equals("SM-T735N") || DeviceType.equals("SM-T515N")) ) {
            slackSenderManager.send(CH_BOT, DeviceType+":비인가 디바이스로 로그에 접속하였습니다." + message);
            throw new CUseridSigninFailedException();
        }
        return logService.sendLogServer(message);
    }
}
