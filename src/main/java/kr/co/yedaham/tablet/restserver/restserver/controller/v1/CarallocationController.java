package kr.co.yedaham.tablet.restserver.restserver.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.*;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import kr.co.yedaham.tablet.restserver.restserver.service.carallocation.CarallocationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = {"15. Carallocation"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class CarallocationController {
    private static final Logger logger = LoggerFactory.getLogger(FunController.class);
    private final CarallocationService carallocationService;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "배차 생성", notes = "배차를 저장하고 SMS 내역을 생성한다")
    @PostMapping(value = "/carallocation/save")
    public CommonResult saveCarallocationSaveSms (@RequestBody CarallocationSaveInfo carallocationSaveInfo) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(carallocationService.saveCarallcationAndSmsHistory(carallocationSaveInfo));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "배차현황 조회", notes = "배차현황 정보를 조회한다")
    @PostMapping(value = "/carallocation/list")
    public CommonResult getCarallocationList (@RequestParam String functrlno) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(carallocationService.getCarallocationList(functrlno));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "반납현황 조회", notes = "반납현황 정보를 조회한다")
    @PostMapping(value = "/carallocation/returnlist")
    public CommonResult getCarallocationReturnList (@RequestParam String functrlno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(carallocationService.getCarallocationReturnList(functrlno));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "배차 물품정보 조회", notes = "배차 물품정보를 조회한다")
    @PostMapping(value = "/carallocation/productinfo")
    public CommonResult getCarallocationProductInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(carallocationService.getCarallocationProductInfo());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "배차 대여정보 조회", notes = "배차 대여정보를 조회한다")
    @PostMapping(value = "/carallocation/rentallist")
    public CommonResult getCarallocationRental(@RequestBody CarallocationRentalRequest carallocationRentalRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(carallocationService.getCarallocationRentalList(carallocationRentalRequest.getFunno(), carallocationRentalRequest.getItemno()));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "배차 대여 저장", notes = "배차 대여를 저장한다")
    @PostMapping(value = "/carallocation/saveRental")
    public CommonResult saveCarallocationProductInfo(@RequestBody List<CarallocationRental> carallocationRental) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(carallocationService.saveCarallocationRental(carallocationRental));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "배차 대여 삭제", notes = "배차 대여를 삭제한다")
    @PostMapping(value = "/carallocation/deleteRental")
    public CommonResult deleteCarallocationRental(@RequestParam long seq) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(carallocationService.deleteCarallocationRental(seq));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "배차 등록 구분 값", notes = "배차등록 구분 값 신규,추가 0 신규, 1 추가, 2 그 외")
    @PostMapping(value = "/carallocation/getValueCarRegister")
    public CommonResult deleteCarallocationRental(@RequestParam String funno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(carallocationService.getValueCarRegister(funno));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "배차 도착 등록 시간", notes = "배차 도착 등록 시간 저장")
    @PostMapping(value = "/carallocation/updateCarRegisterTime")
    public CommonResult updateCarallocationRental(@RequestBody CarallocationRegisterTimeRequest carallocationRegisterTimeRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(carallocationService.updateCarallocation(carallocationRegisterTimeRequest.getFunno(),
                carallocationRegisterTimeRequest.getLine()));
    }



    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "물품 중분류 조회", notes = "물품 중분류 조회한다.")
    @PostMapping(value = "/carallocation/getMiddleCategory")
    public CommonResult getMiddleCategory() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(carallocationService.getMiddleCategory());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "물품 중분류 세부물품 조회", notes = "물품 중분류 세부물품을 조회한다.")
    @PostMapping(value = "/carallocation/getMiddleProduct")
    public CommonResult getMiddleCategoryList(@RequestParam String itemcd) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(carallocationService.getMiddelProductList(itemcd));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "대여현황 실시간 조회", notes = "대여현황 실시간 조회한다.")
    @PostMapping(value = "/carallocation/rentalstatus")
    public CommonResult getRentalStatus(@RequestParam String funno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(carallocationService.getRentalStatus(funno));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "반납물품 묶음라인", notes = "반납물품 묶음라인을 조회한다.")
    @PostMapping(value = "/carallocation/getReturnProduct")
    public CommonResult getRetrunProduct(@RequestParam String funno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(carallocationService.getReturnProduct(funno));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "반납물품 저장", notes = "반납물품을 저장한다.")
    @PostMapping(value = "/carallocation/saveReturn")
    public CommonResult saveReturn(@RequestBody CarallocationReturnDto carallocationReturnDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(carallocationService.saveReturnList(carallocationReturnDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "배차 서명 등록", notes = "배차 서명 등록 저장")
    @PostMapping(value = "/carallocation/updateCarRegisterSignature")
    public CommonResult updateCarallocationRentalSignature(@RequestBody CarallocationSignatureRequest signatureRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(carallocationService.updateCarallocationSignature(signatureRequest.getFunno(),signatureRequest.getLine(), signatureRequest.getSignature(),signatureRequest.getArriveDay(), signatureRequest.getArriveTime()));
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "대여와 반납 수량 비교", notes = "대여와 반납의 수량을 비교")
    @PostMapping(value = "/carallocation/compare")
    public CommonResult getCompare(@RequestParam String funno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(carallocationService.getCompare(funno));
    }

}
