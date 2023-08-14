package kr.co.yedaham.tablet.restserver.restserver.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.yedaham.tablet.restserver.restserver.advice.exception.CUserExistException;
import kr.co.yedaham.tablet.restserver.restserver.model.file.FileFunnoRequest;
import kr.co.yedaham.tablet.restserver.restserver.model.file.FileSiginRequest;
import kr.co.yedaham.tablet.restserver.restserver.model.file.FileUploadResponse;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import kr.co.yedaham.tablet.restserver.restserver.service.carallocation.CarallocationService;
import kr.co.yedaham.tablet.restserver.restserver.service.file.FileUploadDownloadService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Api(tags = {"8. FileControl"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final FileUploadDownloadService serviceImpl;
    private final ResponseService responseService;
    private final CarallocationService carallocationService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "의전 이미지 업로드", notes = "FD전화번호로 의전 이미지 업로드을 한다")
    @PostMapping("/uploadFile")
    public CommonResult uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = serviceImpl.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return responseService.getSingleResult(new FileUploadResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize()));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "의전 이미지 업로드", notes = "특정 핸드폰으로 의전 이미지 업로드을 한다")
    @PostMapping("/uploadFile/phone")
    public CommonResult uploadFilePhone(@RequestParam("file") MultipartFile[] file, @RequestParam("cellphone") String cellPhone, @RequestParam("fileType") String fileType) {

        if(file.length != 2){
            throw new CUserExistException();
        }
        String fileName = serviceImpl.storeFile(file[0]);
        String fileName1 = serviceImpl.storeFile(file[1], cellPhone, fileType);
        serviceImpl.sendFileCustomer(file[1],cellPhone);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName1)
                .toUriString();

        return responseService.getSingleResult(new FileUploadResponse(fileName, fileDownloadUri, file[1].getContentType(), file[1].getSize()));
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){
        Resource resource = serviceImpl.loadFileAsDownload(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @CrossOrigin(origins = {"*"})
    @PostMapping("/downloadFile/check")
    public CommonResult checkDownloadFile(@RequestBody FileFunnoRequest funno, HttpServletRequest request) throws IOException {
        return responseService.getSingleResult(serviceImpl.loadFunnoAsDownloadCheck(funno.getFunno()));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "의전 이미지 업로드", notes = "특정 핸드폰으로 의전 이미지 업로드을 한다")
    @CrossOrigin(origins = {"*"})
    @GetMapping("/downloadFile/receipt1")
    public ResponseEntity<Resource> downloadAppFile1(@RequestParam String funno, @RequestParam String fileType, HttpServletRequest request){
        Resource resource = serviceImpl.loadFunnoAsDownload(funno, fileType);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "배차 서명 업로드", notes = "배차 서명 업로드를 한다")
    @PostMapping("/uploadFile/car")
    public CommonResult uploadFileCar(@RequestParam("file") MultipartFile file, @RequestParam("car") String car) {
        logger.info(car.toString());
        String fileName = serviceImpl.saveCarStoreFile(file);
        return responseService.getSingleResult(new FileUploadResponse(fileName, fileName, file.getContentType(), file.getSize()));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "배차 서명 이미지 등록", notes = "배차 서명 등록 이미지 저장")
    @PostMapping(value = "/uploadFile/saveImageCarSignature")
    public CommonResult updateCarallocationRentalSignature(@RequestParam("file") MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String fileName = serviceImpl.saveCarStoreFile(file);
        return  responseService.getSingleResult(new FileUploadResponse(fileName, fileName, file.getContentType(), file.getSize()));
    }

    @ApiOperation(value = "배차 서명 이미지 불러오기", notes = "배차 서명 이미지 불러오기를 불러온다.")
    @CrossOrigin(origins = {"*"})
    @GetMapping("/downloadFile/carallocation/{fileName:.+}")
    public ResponseEntity<Resource> downloadFileCarSignature(@PathVariable String fileName, HttpServletRequest request){
        Resource resource = serviceImpl.loadCarAsDownload(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @ApiOperation(value = "태블릿 장지 이미지 불러오기", notes = "태블릿 장지 이미지 불러오기를 불러온다.")
    @CrossOrigin(origins = {"*"})
    @GetMapping("/downloadFile/ceme/{fileName:.+}")
    public ResponseEntity<Resource> downloadFileCeme(@PathVariable String fileName, HttpServletRequest request){
        Resource resource = serviceImpl.loadCemeDownload(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @ApiOperation(value = "태블릿 360도 이미지 불러오기", notes = "태블릿 360도 이미지 불러오기를 불러온다.")
    @CrossOrigin(origins = {"*"})
    @GetMapping("/downloadFile/rotate/{fileDiretory:.+}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFileRotate(@PathVariable String fileDiretory,@PathVariable String fileName, HttpServletRequest request){
        Resource resource = serviceImpl.loadRotateDownload(fileDiretory,fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }



    /*
    @CrossOrigin(origins = {"*"})
    @PostMapping("/uploadFile/delete")
    public CommonResult deleteUploadFile(@RequestParam String fileName) throws IOException {
        return responseService.getSingleResult(serviceImpl.deleteUploadFile(fileName));
    }
    */

}