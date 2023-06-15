package kr.co.yedaham.tablet.restserver.restserver.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;
import kr.co.yedaham.tablet.restserver.restserver.model.response.ListResult;
import kr.co.yedaham.tablet.restserver.restserver.model.updatehistory.UpdateHistoryList;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import kr.co.yedaham.tablet.restserver.restserver.service.updatehistory.UpdateHistoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"14. AuditLog"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UpdateHistoryController {

    private final UpdateHistoryServiceImpl serviceImpl;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "로그인 ID로 변경이력 조회", notes = "로그인 ID로 변경이력을 조회한다")
    @PostMapping(value = "/updatehistory/srch")
    public CommonResult findUpdateHistoryList(@RequestParam(required = false) String updateid, @RequestParam String funno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getListResult(serviceImpl.findUpdateHistoryList(updateid, funno));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "로그인 ID로 변경이력 저장", notes = "로그인 ID로 변경이력을 저장한다")
    @PostMapping(value = "/updatehistory/save")
    public CommonResult saveHistoryList(@RequestBody UpdateHistoryList updateHistoryList) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return serviceImpl.insertUpdateInfo(updateHistoryList);
    }
}
