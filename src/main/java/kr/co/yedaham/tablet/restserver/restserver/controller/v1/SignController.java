package kr.co.yedaham.tablet.restserver.restserver.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.yedaham.tablet.restserver.restserver.advice.exception.CUseridSigninFailedException;
import kr.co.yedaham.tablet.restserver.restserver.entity.User;
import kr.co.yedaham.tablet.restserver.restserver.config.restslack.SlackSenderManager;
import kr.co.yedaham.tablet.restserver.restserver.config.security.JwtTokenProvider;
import kr.co.yedaham.tablet.restserver.restserver.model.fun.Subcontractor;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;
import kr.co.yedaham.tablet.restserver.restserver.model.rsa.RsaResponse;
import kr.co.yedaham.tablet.restserver.restserver.model.user.SignUserRequest;
import kr.co.yedaham.tablet.restserver.restserver.resp.UserJpaRepo;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import kr.co.yedaham.tablet.restserver.restserver.service.fun.FunService;
import kr.co.yedaham.tablet.restserver.restserver.service.sms.TabletSmsService;
import kr.co.yedaham.tablet.restserver.restserver.util.RSAUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.PrivateKey;
import java.util.ArrayList;

import static kr.co.yedaham.tablet.restserver.restserver.model.slack.SlackTarget.CH_BOT;

@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {

    private final UserJpaRepo userJpaRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final SlackSenderManager slackSenderManager;
    private final TabletSmsService tabletSmsService;
    private final FunService funService;
    @Value(value = "${rsa.publicKey}")
    private String publicKey;
    @Value(value = "${rsa.privateKey}")
    private String privateKey;



    @ApiOperation(value = "RSA 공개키", notes = "RSA 공개키를 발급한다.")
    @PostMapping(value = "/rsa/public")
    public CommonResult rsaPublic() throws Exception {
         RsaResponse rsaResponse = new RsaResponse();
         rsaResponse.setPublicKey(publicKey);
        return responseService.getSingleResult(rsaResponse);
    }

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = "/signin")
    public CommonResult signin(@RequestBody SignUserRequest request, @RequestHeader String DeviceType,@RequestHeader String deviceAppVersion, @RequestHeader String deviceId) throws Exception {
        if(DeviceType != null && !(DeviceType.equals("SM-T505N") || DeviceType.equals("SM-T720") || DeviceType.equals("SM-T725N") || DeviceType.equals("SM-T500")  || DeviceType.equals("SM-T875N")
                                || DeviceType.equals("SM-T735N") || DeviceType.equals("SM-T515N")) ) {
            slackSenderManager.send(CH_BOT, request.getId() + ":" + DeviceType+":비인가 디바이스로 접속하였습니다.");
            throw new CUseridSigninFailedException();
        }

        RSAUtil rsaUtil = new RSAUtil();
        PrivateKey privateKeyFromBase64Encrypted = rsaUtil.getPrivateKeyFromBase64Encrypted(privateKey);
        String decryptedPassword = RSAUtil.decryptRSA(request.getPassword(), privateKeyFromBase64Encrypted);


        User user = userJpaRepo.findByUserid(request.getId()).orElseThrow(CUseridSigninFailedException::new);

        if (!encryptSha64(decryptedPassword).equals(user.getPassword()))
            throw new CUseridSigninFailedException();

         slackSenderManager.send(CH_BOT,  request.getId()+ "님이 태블릿에 접근하였습니다. (디바이스정보) " + deviceId + " (디바이스버전) " + deviceAppVersion);
       return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getUserid()), user.getRoles()));
    }

    public static String encryptSha64(String base) throws Exception {
        String encryptText = "";
        try{

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            encryptText = hexString.toString();

        } catch(Exception ex){
            throw new RuntimeException(ex);
        }

        return encryptText;
    }

    @ApiOperation(value = "OTP인증", notes = "OTP인증을 한다.")
    @PostMapping(value = "/otp")
    public CommonResult otp(@RequestParam String userId, @RequestParam String otp) throws Exception {
        ArrayList<Subcontractor> subcontractPhoneList = funService.getSubcontractPhoneList(userId);
        return responseService.getSingleResult(tabletSmsService.sendSmsMessage(subcontractPhoneList, otp));
    }

    @ApiOperation(value = "의전번호 로그", notes = "외주업체에서 접근한 의전번호 로그를 남긴다.")
    @PostMapping(value = "/log/funno")
    public CommonResult writeLog(@RequestParam String userId, @RequestParam String funno) throws Exception {
        slackSenderManager.send(CH_BOT, userId + "사번 님이 " + funno + "로 접근 하였습니다.");
        return responseService.getSingleResult(true);
    }
}