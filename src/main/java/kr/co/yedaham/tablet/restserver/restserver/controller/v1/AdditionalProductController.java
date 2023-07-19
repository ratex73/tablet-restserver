package kr.co.yedaham.tablet.restserver.restserver.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.yedaham.tablet.restserver.restserver.model.product.AdditionalPostRequest;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import kr.co.yedaham.tablet.restserver.restserver.service.additionalproduct.AdditionalProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"7. AdditionalProduct"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class AdditionalProductController {
    private final AdditionalProductServiceImpl ProductImpl;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "추가적인 예다함 1호,2호, SK C&C 1,2호 조회", notes = "추가적인 예다함 1,2 SK C&C 1,2호 계약물품을 조회한다.")
    @PostMapping(value = "/additionalproduct/onetwo")
    public CommonResult getProductOneTwoListData(@RequestBody AdditionalPostRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(ProductImpl.getAdditionalProductYedahamOneTwoList(request));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "추가적인 전체 예다함 1호,2호, SK C&C 1,2호 조회", notes = "추가적인 전체 예다함 1,2 SK C&C 1,2호 계약물품을 조회한다.")
    @GetMapping(value = "/additionalproduct/onetwoall")
    public CommonResult getProductOneTwoAllListData(@RequestParam("certno") String certno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(ProductImpl.getAdditionalProductYedahamOneTwoAllList(certno));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "추가적인 두산상품 조회", notes = "추가적인 예다함 두산에 대한 계약물품을 조회한다.")
    @PostMapping(value = "/additionalproduct/dusan")
    public CommonResult getDusanProductListData(@RequestBody AdditionalPostRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(ProductImpl.getAdditionalDusanProductList(request));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "추가적인 전체 두산상품 조회", notes = "추가적인 예다함 두산에 대한 전체 계약물품을 조회한다.")
    @GetMapping(value = "/additionalproduct/dusanall")
    public CommonResult getDusanProductAllListData(@RequestParam("certno") String certno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(ProductImpl.getAdditionalDusanProductAllList(certno));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "추가적인 신상품 조회", notes = "추가적인 예다함 신상품에 대한 계약물품을 조회한다.")
    @PostMapping(value = "/additionalproduct/new")
    public CommonResult getNewProductListData(@RequestBody AdditionalPostRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(ProductImpl.getAdditionalNewProductList(request));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "신상품의 저장된 추가물품 조회", notes = "예다함 신상품에 대한 저장된 추가물품을 조회한다.")
    @GetMapping(value = "/additionalproduct/getInit")
    //public CommonResult getInitNewProductListData(@RequestBody AdditionalPostRequest request) {
    public CommonResult getInitNewProductListData(@RequestParam("prodgb")String prodgb, @RequestParam("functrlno")String functrlno, @RequestParam("certno")String certno) {
    //public CommonResult getInitNewProductListData(@RequestParam("certno")String certno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("============************************======" + certno);
        System.out.println("============************************======" + prodgb);

        CommonResult commonResult;

        if("dusan".equals(prodgb)) {
            System.out.println("============*****dusan**dusan***dusan**************======");
            commonResult = responseService.getListResult(ProductImpl.getInitAdditionalDusanProductList(functrlno, certno));
        }
        else if("onetwo".equals(prodgb)) {
            System.out.println("============*****onetwo**************======");
            commonResult = responseService.getListResult(ProductImpl.getInitAdditionaOneTwoProductList(functrlno, certno));
        }
        else {
            commonResult = responseService.getListResult(ProductImpl.getInitAdditionalNewProductList(functrlno, certno));
        }
        return commonResult;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "추가적인 전체 신상품 조회", notes = "추가적인 예다함 신상품에 대한 모든 물품을 조회한다.")
    @GetMapping(value = "/additionalproduct/newall")
    public CommonResult getNewProductListData(@RequestParam("certno") String certno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(ProductImpl.getAdditionalNewProductAllList(certno));
    }

}
