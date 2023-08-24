package kr.co.yedaham.tablet.restserver.restserver.controller.v1;

import com.ubireport.viewer.report.preview.UbiViewer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.yedaham.tablet.restserver.restserver.config.security.JwtTokenProvider;
import kr.co.yedaham.tablet.restserver.restserver.model.file.FileUploadResponse;
import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.FunItemCalcDto;
import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.FunItemInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import kr.co.yedaham.tablet.restserver.restserver.service.file.FileUploadDownloadService;
import kr.co.yedaham.tablet.restserver.restserver.service.funCalc.FunCalcService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.util.List;

@Api(tags = {"18. FunCalc"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")

public class FunCalcController {
    private static final Logger logger = LoggerFactory.getLogger(FunMessageController.class);
    private final FunCalcService funCalcService;

    private final FileUploadDownloadService fileUploadDownloadService;
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

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "의전정산서 이미지 업로드", notes = "서명된 의전정산서 이미지를 업로드 한다")
    @PostMapping("/funCalc/uploadFunReport")
    public CommonResult uploadSignedFunReportFile(@RequestParam("file") MultipartFile[] file,  @RequestParam("cellphone") String cellPhone, @RequestParam("fileType") String fileType) {

        logger.info("######## Start uploadSignedFunReportFile ########");
        String fileName = fileUploadDownloadService.storeFile(file[0]);
        String fileName1 = fileUploadDownloadService.storeFile(file[1], cellPhone, fileType);
        fileUploadDownloadService.sendFileCustomer(file[1],cellPhone);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName1)
                .toUriString();

        logger.info("######## End uploadSignedFunReportFile ########");

        return responseService.getSingleResult(new FileUploadResponse(fileName, fileDownloadUri, file[1].getContentType(), file[1].getSize()));
    }

    @GetMapping(value = "/saveFunReport")
    protected void saveFile(String functrlno, String josonYn) {

        //저장 성공 여부
        boolean isSuccess = false;
        //웹어플리케이션 Root URL, ex)http://localhost:8080/myapp
        String appUrl = "http://192.168.100.198:8080/";
        String appPath = "";

        /*
        //웹어플리케이션명
        String appName = StrUtil.nvl(request.getContextPath(), "");
        if( appName.indexOf("/") == 0 ) {
            appName = appName.substring(1, appName.length());
        }

        //웹어플리케이션 Root URL, ex)http://localhost:8080/myapp
        String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + ((appName.length() == 0)?"":"/") + appName;

        //웹어플리케이션 Root Path, ex)/webapp/myapp
        String appPath = request.getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/");
        if( appPath.lastIndexOf("/") == (appPath.length() - 1) ) {
            appPath = appPath.substring(0, appPath.lastIndexOf("/"));
        }
        */

        String file = "FSFU1007_3_1_RENEWAL_T.jrf";

        //조손가정
        if("Y".equals(josonYn)) {
            file = "FSFU1007_3_3_T.jrf";
        }

        if("".equals(functrlno)) {
            functrlno = "2022010063";
        }

        //UI에서 호출될 때 필요한 정보
        String jrf = file;
        String arg = "functrl_no#" + functrlno + "#";

        //환경에 맞게 설정해야함

        String serverUrl = (appUrl + "UbiServer");
        String fileUrl = (appUrl + "/ubi4/resource");
        String resource = "fixed";
        String jrfDir = (appPath + "E:/ubireport/UbiService/work/");
        String dataSource = "tablet";

        String exportFileType = "PDF";
        String exportPath = appPath + "/ubi4/storage/";
        String exportFileName = "funReport_" + functrlno + ".pdf";
        String exportFilePath = "E:/uploads/" + exportFileName;


        logger.info("[appUrl] " + appUrl);
        logger.info("[appPath] " + appPath);
        logger.info("[jrfDir] " + jrfDir);
        logger.info("[jrf] " + jrf);
        logger.info("[arg] " + arg);
        logger.info("[type] " + exportFileType);
        logger.info("[exportDir] " + exportFilePath);


        try {

            UbiViewer ubi = new UbiViewer(false, false);

            ubi.exectype = "TYPE6";
            ubi.fileURL = fileUrl;
            ubi.resource = resource;
            ubi.ubiServerURL = serverUrl;
            ubi.isLocalFile = true;
            ubi.dataSource = dataSource;
            ubi.jrfFileDir = jrfDir;
            ubi.jrfFileName = jrf;
            ubi.arg = arg;
            ubi.setExportParams(exportFileType, exportFilePath);

            //Windows OS가 아닌 경우 폰트 경로 설정을 해야함
            if( System.getProperty("os.name").indexOf("Win") == -1 ) {

                //ubi.setFontPath("/ubireport/UbiService/fonts");
                ubi.setFontPath("E:/ubireport/UbiService/fonts");
            }
            isSuccess = ubi.loadReport();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if(isSuccess){
                logger.info("File Export Success");
            } else{
                logger.info("File Export Fail");
            }
        }

    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "의전정산서 파일 전송가능여부", notes = "최종 의전정산서파일 전송 가능여부를 확인한다.")
    @GetMapping("/funCalc/getFunReportSendYn")
    public CommonResult funReportSendYn(@RequestParam("functrlno") String functrlno, @RequestParam("fileType") String fileType) {

        logger.info("######## Start funReportSendYn ########");
        String sendYn = funCalcService.funReportSendYn(functrlno, fileType);

        logger.info("sendYn : " + sendYn);
        logger.info("######## End funReportSendYn ########");
        return responseService.getSingleResult(sendYn);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "의전 관련 파일명 조회", notes = "상품설명서, 의전정산서 등 파일명을 조회한다.")
    @GetMapping("/funCalc/getFunFileName")
    public CommonResult getFunFileName(@RequestParam("functrlno") String functrlno, @RequestParam("fileType") String fileType) {

        logger.info("######## Start funReportSendYn ########");
        String sendYn = funCalcService.getFunFileName(functrlno, fileType);

        logger.info("sendYn : " + sendYn);
        logger.info("######## End funReportSendYn ########");
        return responseService.getSingleResult(sendYn);
    }

}

