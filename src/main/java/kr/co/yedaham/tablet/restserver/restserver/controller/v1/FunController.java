package kr.co.yedaham.tablet.restserver.restserver.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.yedaham.tablet.restserver.restserver.model.file.FileSiginRequest;
import kr.co.yedaham.tablet.restserver.restserver.model.fun.*;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import kr.co.yedaham.tablet.restserver.restserver.service.fun.FunServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = {"3. Fun"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class FunController {
    private static final Logger logger = LoggerFactory.getLogger(FunController.class);
    private final FunServiceImpl serviceImpl;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "의전 조회", notes = "모든 의전을 조회한다")
    @PostMapping(value = "/funprogress")
    public CommonResult getFunListData (@RequestBody FunPostRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(serviceImpl.getFunList(request));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "외주업체 의전 조회", notes = "외주업체 모든 의전을 조회한다")
    @PostMapping(value = "/subcontract/funlist")
    public CommonResult getSubcontractFunListData (@RequestParam String userid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(serviceImpl.getSubcontractFunList(userid));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "리무진 버스 차량 정보 조회", notes = "리무진 버스 차량 정보를 조회한다")
    @PostMapping(value = "/funcar/info")
    public CommonResult getFunCarInfoData (@RequestParam String functrlno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(serviceImpl.findGetFunCarInfo(functrlno));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "진행정보1 조회", notes = "진행정보1를 조회한다")
    @PostMapping(value = "/funprogress/info")
    public CommonResult getFunDetailInfoData (@RequestParam String funno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(serviceImpl.findFunProgressInfo(funno));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "고인정보,상주정보,유가족정보 조회", notes = "고인정보,상주정보,유가족정보 조회한다")
    @PostMapping(value = "/funcustomer/info")
    public CommonResult getFunCustomerInfoData (@RequestParam String funno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(serviceImpl.findFunCustomerInfo(funno));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "장례식장, 앰뷸런스 정보 조회", notes = "장례식장, 앰뷸런스 정보 조회한다")
    @PostMapping(value = "/funeral/info1")
    public CommonResult getFunInfo1Data (@RequestParam String funno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(serviceImpl.findFuneralInfo1(funno));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "장법, 장지 정보 조회", notes = "장법, 장지 정보 조회한다")
    @PostMapping(value = "/funeral/info2")
    public CommonResult getFunInfo2Data (@RequestParam String funno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(serviceImpl.findFuneralInfo2(funno));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "초도, 입관, 발인인력 조회", notes = "초도, 입관, 발인인력을 조회한다")
    @PostMapping(value = "/funeral/workerinfo")
    public CommonResult findFunWorkerInfo (@RequestParam String functrlno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(serviceImpl.findFunWorkerInfo(functrlno));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "의전시작/종료 문자 발송", notes = "의전시작/종료 문자를 발송한다")
    @PostMapping(value = "/funeral/funsendsms")
    public CommonResult sendFunSms (@RequestBody FunSmsInfo funSmsInfo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(serviceImpl.sendFunSms(funSmsInfo));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "의전시작/종료 문자 대상자 조회", notes = "의전시작/종료 문자 대상자를 조회한다")
    @PostMapping(value = "/funeral/funsms")
    public CommonResult getFunSmsEmpList (@RequestParam String funno, @RequestParam String smsType) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(serviceImpl.getFunSmsEmpList(funno, smsType));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "고인정보 수정", notes = "고인정보를 수정한다")
    @PostMapping(value = "/departed/update")
    public CommonResult updateDeparted (@RequestBody DepartedInfo departedInfo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(serviceImpl.updateDeparted(departedInfo));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "앰뷸런스정보 수정", notes = "앰뷸런스정보를 수정한다")
    @PostMapping(value = "/ambulance/update")
    public CommonResult updateAmbulance (@RequestBody AmbulanceInfo ambulanceInfo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(serviceImpl.updateAmbulanceInfo(ambulanceInfo));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "funno", value = "의전번호", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "funMeth", value = "매장방법코드", required = false, dataType = "string", paramType = "query")
    })
    @ApiOperation(value = "장지정보 수정", notes = "장지정보를 수정한다")
    @PostMapping(value = "/burial/update")
    public CommonResult updateBurial (@RequestBody BurialInfo burialInfo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(serviceImpl.updateBurialInfo(burialInfo));
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "장의차량정보 수정", notes = "장의차량 사용구분을 수정한다")
    @PostMapping(value = "/funcar/update")
    public CommonResult updateFunCarInfo (@RequestBody FunCarUpdateInfo funCarUpdateInfo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(serviceImpl.updateFunCarlInfo(funCarUpdateInfo));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "상품정보 서명시 고객 데이터 저장", notes = "상품정보 서명시 고객 데이터를 저장한다.")
    @PostMapping("/file/signin")
    public CommonResult fileSign(@RequestBody FileSiginRequest fileSiginRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(serviceImpl.fileSignin(fileSiginRequest));
    }
}
