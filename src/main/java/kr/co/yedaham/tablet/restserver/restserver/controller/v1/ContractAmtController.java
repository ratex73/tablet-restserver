package kr.co.yedaham.tablet.restserver.restserver.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.yedaham.tablet.restserver.restserver.model.contractamt.Contract;
import kr.co.yedaham.tablet.restserver.restserver.model.contractamt.ContractAmt;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import kr.co.yedaham.tablet.restserver.restserver.service.contractamt.ContractAmtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"6. Contract"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class ContractAmtController {

    private final ContractAmtServiceImpl serviceImpl;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "계약금액 조회", notes = "계약된 금액을 조회한다")
    @GetMapping(value = "/contractamt")
    public CommonResult getFunListData (@RequestParam("certno")String certno, @RequestParam("functrlno")String functrlno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(serviceImpl.getContractAmt(certno, functrlno));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "계약상세 조회", notes = "계약상세를 조회한다")
    @GetMapping(value = "/contract")
    public CommonResult getContractData (@RequestParam("certno")String certno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return responseService.getSingleResult(serviceImpl.getContract(certno));
    }
}
