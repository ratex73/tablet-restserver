package kr.co.yedaham.tablet.restserver.restserver.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.yedaham.tablet.restserver.restserver.model.comm.CoComSrch1Request;
import kr.co.yedaham.tablet.restserver.restserver.model.comm.CoComSrch2Request;
import kr.co.yedaham.tablet.restserver.restserver.model.comm.FunCoComList;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;
import kr.co.yedaham.tablet.restserver.restserver.model.response.ListResult;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import kr.co.yedaham.tablet.restserver.restserver.service.comm.CommServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"10. Common"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class CommController {

    private final CommServiceImpl serviceImpl;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "행정구역으로 의전 협력업체 조회", notes = "행정구역으로 의전 협력업체를 조회한다")
    @PostMapping(value = "/comm/cocomsrch1")
    public CommonResult findFunCoComList(@RequestBody CoComSrch1Request request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(serviceImpl.findFunCoComList(request.getFungb(), request.getFrareacd(), request.getToareacd()));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "검색어로 의전 협력업체 조회", notes = "검색어로 의전 협력업체를 조회한다")
    @PostMapping(value = "/comm/cocomsrch2")
    public CommonResult findFunCoComListByName(@RequestBody CoComSrch2Request request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(serviceImpl.findFunCoComListByName(request.getFunnm(), request.getAddr(), request.getTelno()));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "장지정보 조회", notes = "장지정보를 조회한다.")
    @PostMapping(value = "/comm/ceme/list")
    public CommonResult getCommCemeList(@RequestParam String funCd) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(serviceImpl.getCommCemeList(funCd));
    }
}
