package kr.co.yedaham.tablet.restserver.restserver.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.yedaham.tablet.restserver.restserver.entity.MourningEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.comm.MourningFunDayTimeInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.mourning.MourningInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;
import kr.co.yedaham.tablet.restserver.restserver.model.response.SingleResult;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import kr.co.yedaham.tablet.restserver.restserver.service.mourning.MourningService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@Api(tags = {"12. Mourning"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class MourningController {
    private final MourningService mourningService;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "상복도착희망일시 저장", notes = "상복도착희망일시 등 저장한다")
    @PostMapping(value = "/mourning/save")
    public CommonResult saveMourning (@RequestBody MourningFunDayTimeInfo mourningInfo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(mourningService.saveMourning(mourningInfo));
    }
}
