package kr.co.yedaham.tablet.restserver.restserver.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.yedaham.tablet.restserver.restserver.model.customer.*;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;
import kr.co.yedaham.tablet.restserver.restserver.model.response.ListResult;
import kr.co.yedaham.tablet.restserver.restserver.model.response.SingleResult;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import kr.co.yedaham.tablet.restserver.restserver.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"9. Customer"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "고객정보 조회", notes = "고객정보 조회를 한다.")
    @PostMapping("/customerinfo")
    public CommonResult getCustomerInfoData (@RequestParam("functrlno")String functrlno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(customerService.getCustomerInfo(functrlno));    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "고객 납입 정보 조회", notes = "고객 납입 정보 조회를 한다.")
    @PostMapping("/customer/paymentinfo")
    public CommonResult getPaymentInfo (@RequestParam("certno")String certno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(customerService.getPaymentInfo(certno));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "법인 정보 조회", notes = "법인 정보 조회를 한다.")
    @PostMapping("/corporation/info")
    public CommonResult getCorporationInfo (@RequestParam("custid")String custid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(customerService.getCorporationInfo(custid));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "법인 상담자정보 조회", notes = "법인 상담자정보 조회를 한다.")
    @PostMapping("/corporation/customerinfo")
    public CommonResult getCorporationCustomerInfo (@RequestParam("custid")String custid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(customerService.getCorporationCustomerInfo(custid));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "법인 지원대상자확인방법 조회", notes = "법인 지원대상자확인방법 조회를 한다.")
    @PostMapping("/corporation/supportinfo")
    public CommonResult getCorporationSupportInfo (@RequestParam("custid")String custid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(customerService.getCorporationSupportInfo(custid));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "법인 일회용품 조회", notes = "법인 일회용품 조회를 한다.")
    @PostMapping("/corporation/disposableinfo")
    public CommonResult getCorporationDisposableInfo (@RequestParam("custid")String custid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(customerService.getCorporationDisposableInfo(custid));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "법인 제품지원범위 조회", notes = "법인 제품지원범위 조회를 한다.")
    @PostMapping("/corporation/productinfo")
    public CommonResult getCorporationRangeProductSupportInfo (@RequestParam("custid")String custid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(customerService.getCorporationRangeProductSupportInfo(custid));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "법인 결제방법 조회", notes = "법인 결제방법 조회를 한다.")
    @PostMapping("/corporation/paymentmethodinfo")
    public CommonResult getCorporationPaymentMethodInfo(@RequestParam("custid")String custid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(customerService.getCorporationPaymentMethodInfo(custid));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "법인 파일다운로드정보 조회", notes = "법인 파일다운로드정보 조회를 한다.")
    @PostMapping("/corporation/filedownloadinfo")
    public CommonResult getCorporationFiledownloadInfo(@RequestParam("custid")String custid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(customerService.getCorporationFiledownloadInfo(custid));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "법인 주의사항 조회", notes = "법인 주의사항 조회를 한다.")
    @PostMapping("/corporation/preacutionsinfo")
    public CommonResult getCorporationPrecautionsInfo(@RequestParam("custid")String custid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(customerService.getCorporationPrecautionsInfo(custid));
    }


}
