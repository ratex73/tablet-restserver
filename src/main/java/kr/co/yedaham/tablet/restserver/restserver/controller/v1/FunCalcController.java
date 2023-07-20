package kr.co.yedaham.tablet.restserver.restserver.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.yedaham.tablet.restserver.restserver.config.security.JwtTokenProvider;
import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.FunItemCalcDto;
import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.FunItemInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import kr.co.yedaham.tablet.restserver.restserver.service.funCalc.FunCalcService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"18. FunCalc"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")

public class FunCalcController {
    private static final Logger logger = LoggerFactory.getLogger(FunMessageController.class);
    private final FunCalcService funCalcService;
    private final ResponseService responseService;

    private final JwtTokenProvider jwtTokenProvider;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "의전물품 저장", notes = "의전물품 저장")
    @PostMapping(value = "/funCalc/saveFunItem")
    public CommonResult funCalcSave(@RequestBody List<FunItemInfo> funItemInfoList) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return responseService.getSingleResult(funCalcService.saveFunCalc(funItemInfoList));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "의전물품 및 결산 저장", notes = "의전물품 및 정산금액 저장")
    @PostMapping(value = "/funCalc/saveFunItemlCalc")
    public CommonResult funItemCalcSave(@RequestBody FunItemCalcDto funItemCalcDto) throws Exception {

        CommonResult commResult;

        try {
            logger.info("######## Start funItemCalcSave ########");
            logger.info("##################" + funItemCalcDto.toString());

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            commResult = funCalcService.saveFunItemCalc(funItemCalcDto);

            logger.info("saveFunItemCalc Result : " + responseService.getSuccessResult().toString());

        } catch(Exception e) {
            //e.printStackTrace();
            logger.info("######## Error funItemCalcSave ########");
            logger.info(e.getMessage());
            throw e;
        }

        return commResult;
    }

    @ApiOperation(value = "의전물품 및 결산 저장", notes = "의전물품 및 정산금액 저장")
    @PostMapping(value = "/funCalc/saveFunItemlCalc2")
    public CommonResult funItemCalcSave2(@RequestBody FunItemCalcDto funItemCalcDto) throws Exception {

        try {
            logger.info("######## Start funItemCalcSave ########");
            logger.info("##################" + funItemCalcDto.toString());

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        } catch(Exception e) {
            //e.printStackTrace();
            System.out.println("######## Error funItemCalcSave ########");
            System.out.println(e.getMessage());
            throw e;
        }

        return responseService.getSingleResult(funCalcService.saveFunItemCalc(funItemCalcDto));
    }
}

