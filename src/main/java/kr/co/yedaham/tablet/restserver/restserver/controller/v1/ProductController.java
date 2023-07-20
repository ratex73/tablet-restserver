package kr.co.yedaham.tablet.restserver.restserver.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.yedaham.tablet.restserver.restserver.model.product.ContractList;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import kr.co.yedaham.tablet.restserver.restserver.service.product.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Api(tags = { "4. Product" })
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class ProductController {
    private final ProductServiceImpl ProductImpl;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "예다함 1호,2호 조회", notes = "예다함 1호,2호에 대한 계약물품을 조회한다.")
    @GetMapping(value = "/product/onetwo")
    public CommonResult getProductOneTwoListData(@RequestParam("certno") String certno,
            @RequestParam("functrlno") String functrlno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(ProductImpl.getProductYedahamOneTwoList(certno, functrlno));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "두산상품 조회", notes = "예다함 두산에 대한 계약물품을 조회한다.")
    @GetMapping(value = "/product/dusan")
    public CommonResult getDusanProductListData(@RequestParam("certno") String certno, @RequestParam("functrlno") String functrlno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(ProductImpl.getDusanProductList(certno, functrlno));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "신상품 조회", notes = "예다함 신상품에 대한 계약물품을 조회한다.")
    @GetMapping(value = "/product/new")
    public CommonResult getNewProductListData(@RequestParam("certno") String certno,
            @RequestParam("functrlno") String functrlno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(ProductImpl.getNewProductList(certno, functrlno));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "자율선택 조회", notes = "예다함 신상품에 대한 계약물품을 조회한다.")
    @GetMapping(value = "/product/optional")
    public CommonResult getOptionalProductListData(@RequestParam("certno") String certno,
            @RequestParam("functrlno") String functrlno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(ProductImpl.getOptionalProductList(certno, functrlno));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "증서조회", notes = "가입한 증서 이외의 증서들을 조회한다.")
    @PostMapping(value = "/product/contractDetail")
    public CommonResult getContractDetailData(@RequestParam("certno") String certNo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(ProductImpl.getContractDetailList(certNo));
    }
    
}
