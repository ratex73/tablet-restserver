package kr.co.yedaham.tablet.restserver.restserver.service.file;

import kr.co.yedaham.tablet.restserver.restserver.advice.exception.CFileNotExistException;
import kr.co.yedaham.tablet.restserver.restserver.config.file.FileUploadProperties;
import kr.co.yedaham.tablet.restserver.restserver.config.restslack.SlackSenderManager;
import kr.co.yedaham.tablet.restserver.restserver.config.sms.SendProperties;
import kr.co.yedaham.tablet.restserver.restserver.controller.v1.FunMessageController;
import kr.co.yedaham.tablet.restserver.restserver.entity.FunMessageEntity;
import kr.co.yedaham.tablet.restserver.restserver.entity.FunSigninEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.file.FileDownloadException;
import kr.co.yedaham.tablet.restserver.restserver.model.file.FileSiginRequest;
import kr.co.yedaham.tablet.restserver.restserver.model.file.FileUploadException;
import kr.co.yedaham.tablet.restserver.restserver.model.slack.SlackTarget;
import kr.co.yedaham.tablet.restserver.restserver.resp.fun.FunSigininResp;
import kr.co.yedaham.tablet.restserver.restserver.service.sms.TabletSmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class FileUploadDownloadServiceImpl implements FileUploadDownloadService{

    private static final Logger logger = LoggerFactory.getLogger(FileUploadDownloadServiceImpl.class);
    private final Path fileLocation;
    @Autowired
    private TabletSmsService tabletSmsService;
    private SlackSenderManager slackSenderManager;

    @Autowired
    private SendProperties sendProp;



    public FileUploadDownloadServiceImpl(FileUploadProperties prop) {
        this.fileLocation = Paths.get(prop.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileLocation);
        } catch (Exception e) {
            throw new FileUploadException(" 파일을 업로드할 디렉토리를 생성하지 못했습니다.", e);
        }
    }

    public String storeFile(MultipartFile file, String cellPhone, String fileType) {

        try{
            logger.info("######## Start storeFile ########");
            tabletSmsService.smsInsert(file.getOriginalFilename(), file.getOriginalFilename().substring(0,10), cellPhone, fileType);
        } catch (Exception ex){
            slackSenderManager.send(SlackTarget.CH_BOT, "storeFile" + ex.getMessage());
        }

        String funNumber = "";

        if(StringUtils.hasText(file.getOriginalFilename())){
            funNumber = file.getOriginalFilename().substring(0,10);
        }
        else
            throw new NullPointerException(" 파일을 업로드할 디렉토리를 생성하지 못했습니다.");

        Path target = Paths.get(fileLocation.toString(), funNumber);

        try {
            Files.createDirectories(target);
        } catch (Exception e) {
            throw new FileUploadException(" 파일을 업로드할 디렉토리를 생성하지 못했습니다.", e);
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains(".."))
                throw new FileUploadException("파일명에 부적합 문자가 포함되어 있습니다." + fileName);


            Path targetLocation = Paths.get(this.fileLocation.toString(), funNumber, fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception e){
            throw new FileUploadException("["+fileName+"] 파일 업로드에 실패하였습니다. 다시 시도하십시오.",e);
        }
    }

    public String storeFile(MultipartFile file, String cellPhone) {

        try{
            logger.info("######## Start storeFile Item File ########");
            tabletSmsService.smsInsert(file.getOriginalFilename(), file.getOriginalFilename().substring(0,10), cellPhone, "ITEM");
        } catch (Exception ex){
            slackSenderManager.send(SlackTarget.CH_BOT, "storeFile" + ex.getMessage());
        }


        String funNumber = "";

        if(StringUtils.hasText(file.getOriginalFilename())){
            funNumber = file.getOriginalFilename().substring(0,10);
        }
        else
            throw new NullPointerException(" 파일을 업로드할 디렉토리를 생성하지 못했습니다.");

        Path target = Paths.get(fileLocation.toString(), funNumber);

        try {
            Files.createDirectories(target);
        } catch (Exception e) {
            throw new FileUploadException(" 파일을 업로드할 디렉토리를 생성하지 못했습니다.", e);
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains(".."))
                throw new FileUploadException("파일명에 부적합 문자가 포함되어 있습니다." + fileName);


            Path targetLocation = Paths.get(this.fileLocation.toString(), funNumber, fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception e){
            throw new FileUploadException("["+fileName+"] 파일 업로드에 실패하였습니다. 다시 시도하십시오.",e);
        }
    }

    public String storeFile(MultipartFile file) {
        String funNumber = "";

        if(StringUtils.hasText(file.getOriginalFilename())){
            funNumber = file.getOriginalFilename().substring(0,10);
        }
        else
            throw new NullPointerException(" 파일을 업로드할 디렉토리를 생성하지 못했습니다.");

        Path target = Paths.get(fileLocation.toString(), funNumber);

        try {
            Files.createDirectories(target);
        } catch (Exception e) {
            throw new FileUploadException(" 파일을 업로드할 디렉토리를 생성하지 못했습니다.", e);
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains(".."))
                throw new FileUploadException("파일명에 부적합 문자가 포함되어 있습니다." + fileName);


            Path targetLocation = Paths.get(this.fileLocation.toString(), funNumber, fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception e){
            throw new FileUploadException("["+fileName+"] 파일 업로드에 실패하였습니다. 다시 시도하십시오.",e);
        }
    }

    public Resource loadFileAsDownload(String fileName) {
        try {

            String funNumber = fileName.substring(0,10);
            Path filePath = Paths.get(this.fileLocation.toString(), funNumber, fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()) {
                return resource;
            } else {
                slackSenderManager.send(SlackTarget.CH_BOT, fileName + " 파일을 찾을 수 없습니다.");
                throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.");
            }
        } catch (MalformedURLException e) {
            slackSenderManager.send(SlackTarget.CH_BOT, fileName + " 파일을 찾을 수 없습니다.");
            throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.", e);
        }
    }

    @Override
    public Resource loadFunnoAsDownload(String functrlno, String fileType) {
        try {
            Path filePath = Paths.get(this.fileLocation.toString(), functrlno);
            String fileName = "";
            String containFileName = "encrypt";
            
            Set<String> fileList = new HashSet<>();
            
            if("CALC".equals(fileType)) { //의전정산서
                containFileName = "report_fd";
            }

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(filePath)) {
                for (Path path : stream) {
                    if (!Files.isDirectory(path)) {
                        if(path.getFileName().toString().contains(containFileName))
                            continue;
                        fileName = path.getFileName().toString();
                        fileList.add(path.getFileName()
                                .toString());
                    }
                }
            }

            Path filePath1 = Paths.get(this.fileLocation.toString(), functrlno, fileName);

            Resource resource = new UrlResource(filePath1.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileDownloadException(filePath + " 파일을 찾을 수 없습니다.");
            }
        } catch (MalformedURLException e) {
            throw new FileDownloadException(functrlno + " 파일을 찾을 수 없습니다.", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean loadFunnoAsDownloadCheck(String functrlno) {
        boolean result = false;
        try {
            Path filePath = Paths.get(this.fileLocation.toString(), functrlno);
            String fileName = "";
            Set<String> fileList = new HashSet<>();
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(filePath)) {
                for (Path path : stream) {
                    if (!Files.isDirectory(path)) {
                        if(path.getFileName().toString().contains("encrypt"))
                            continue;
                        fileName = path.getFileName().toString();
                        fileList.add(path.getFileName()
                                .toString());
                    }
                }
            }

            Path filePath1 = Paths.get(this.fileLocation.toString(), functrlno, fileName);

            Resource resource = new UrlResource(filePath1.toUri());
            if (resource.exists()) {
                result = true;
            }
        } catch (MalformedURLException e) {
            result = false;
        } catch (IOException e) {
            result = false;
        }
        return result;
    }

    @Override
    public Resource loadCarAsDownload(String filename) {
        String funNumber = filename.substring(0,10);

        try {

            Path filePath = Paths.get(this.fileLocation.toString(), "signature",funNumber, filename);

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {

                return resource;
            } else {
                throw new CFileNotExistException();
            }
        } catch (MalformedURLException e) {
            System.out.println("exception error");
            throw new CFileNotExistException();
        } catch (IOException e) {
            System.out.println("Exception error");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean sendFileCustomer(MultipartFile file, String cellPhone) {
         boolean result = false;
         try {
               String mmsMsg = sendProp.getUrl() + file.getOriginalFilename();
               result = tabletSmsService.sendSmsMessage(cellPhone, mmsMsg);
         }catch (Exception e) {
              System.out.println("Exception error");
              e.printStackTrace();
         }
        return result;
    }

    @Override
    public Resource loadCemeDownload(String filename) {
        String funCd = filename.substring(0,9);

        try {

            Path filePath = Paths.get(this.fileLocation.toString(), "site",funCd, filename);

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {

                return resource;
            } else {
                throw new CFileNotExistException();
            }
        } catch (MalformedURLException e) {
            System.out.println("exception error");
            throw new CFileNotExistException();
        } catch (IOException e) {
            System.out.println("Exception error");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Resource loadRotateDownload(String fileDiretory,String filename) {
       // String[] splited = filename.split("_");

        try {
         //   Path filePath = Paths.get(this.fileLocation.toString(), "rotate",splited[0], splited[1]);
            Path filePath = Paths.get(this.fileLocation.toString(), "rotate",fileDiretory, filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {

                return resource;
            } else {
                throw new CFileNotExistException();
            }
        } catch (MalformedURLException e) {
            System.out.println("exception error");
            throw new CFileNotExistException();
        } catch (IOException e) {
            System.out.println("Exception error");
            e.printStackTrace();
        }
        return null;
    }


    /*
    @Override
    public boolean deleteUploadFile(String fileName) {
        boolean result = false;
        String functrlno = fileName.substring(0,10);
        try {
              String filePath = this.fileLocation.toString() + "/signature/"  +functrlno + "/" + fileName;
              File deleteFile = new File(filePath);
              if(deleteFile.exists()) {
                deleteFile.delete();
                result = true;
              }
        }catch (Exception e) {
            System.out.println("Exception error");
            e.printStackTrace();
        }
        return result;
    }
     */


    public String saveCarStoreFile(MultipartFile file) {
        String funNumber = "";
        if(StringUtils.hasText(file.getOriginalFilename())){
            funNumber = file.getOriginalFilename().substring(0,10);
        }
        else
            throw new NullPointerException(" 파일을 업로드할 디렉토리를 생성하지 못했습니다.");
        Path target = Paths.get("/usr/local/src/tablet-restserver/uploads/signature", funNumber);

        try {
            Files.createDirectories(target);
        } catch (Exception e) {
            throw new FileUploadException(" 파일을 업로드할 디렉토리를 생성하지 못했습니다.", e);
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains(".."))
                throw new FileUploadException("파일명에 부적합 문자가 포함되어 있습니다." + fileName);
            Path targetLocation = Paths.get("/usr/local/src/tablet-restserver/uploads/signature", funNumber,fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception e){
            throw new FileUploadException("["+fileName+"] 파일 업로드에 실패하였습니다. 다시 시도하십시오.",e);
        }
    }
}
