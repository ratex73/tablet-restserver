package kr.co.yedaham.tablet.restserver.restserver.controller.v1;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.yedaham.tablet.restserver.restserver.model.commute.CommuteRequest;
import kr.co.yedaham.tablet.restserver.restserver.model.commute.CommuteSaveInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import kr.co.yedaham.tablet.restserver.restserver.service.commute.CommuteService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Api(tags = {"16. Commute"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class CommuteController {

    private static final Logger logger = LoggerFactory.getLogger(CommuteController.class);
    private final CommuteService commuteService;
    private final ResponseService responseService;

      @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "출,퇴근 조회", notes = "관리자가 출,퇴근을 조회한다")
    @PostMapping(value = "/commute/list")
    public CommonResult getCommuteList (@RequestBody CommuteRequest commuteRequest) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(commuteService.getCommuteList(commuteRequest));
    }

      @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "출근시간 저장", notes = "FD의 출근시간을 저장한다")
    @PostMapping(value = "/commute/start/save")
    public CommonResult commuteStartSave (@RequestBody CommuteSaveInfo commuteSaveInfo) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(commuteService.commuteStartSaveInfo(commuteSaveInfo));
    }

        @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "퇴근시간 저장", notes = "FD의 퇴근시간을 저장한다")
    @PostMapping(value = "/commute/end/save")
    public CommonResult commuteEndSave (@RequestBody CommuteSaveInfo commuteSaveInfo) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(commuteService.commuteEndSaveInfo(commuteSaveInfo));
    }

      @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "부서 목록", notes = "소속된 부서를 조회한다.")
    @PostMapping(value = "/commute/deptList")
    public CommonResult getCommuteDeptList(@RequestParam String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(commuteService.getDeptList(userId));
    }

     @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "출근취소 목록", notes = "출근취소를 위한 목록을 조회한다..")
    @PostMapping(value = "/commute/cancelList")
    public CommonResult getCommuteCancelList(@RequestParam String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(commuteService.getCommuteCancelList(userId));
    }

        @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "출,퇴근 조회", notes = "FD가 출,퇴근을 조회한다")
    @PostMapping(value = "/commute/fdList")
    public CommonResult getCommuteFdList (@RequestParam String funCtrlNo) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(commuteService.getCommuteFdList(funCtrlNo));
    }

     @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "출근일자 수정", notes = "fd의 출근일자를 수정한다")
    @PostMapping(value = "/commute/updateFundays")
    public CommonResult updateFunCarInfo (@RequestParam long seq, @RequestParam String funDays) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(commuteService.updateCommuteFunDays(seq,funDays));
    }
}
