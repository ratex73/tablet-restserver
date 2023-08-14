package kr.co.yedaham.tablet.restserver.restserver.service.file;

import kr.co.yedaham.tablet.restserver.restserver.model.file.FileSiginRequest;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadDownloadService {
    String saveCarStoreFile(MultipartFile file);
    String storeFile(MultipartFile file);
    String storeFile(MultipartFile file, String cellPhone);
    String storeFile(MultipartFile file, String cellPhone, String fileType);
    Resource loadFileAsDownload(String fileName);
    //Resource loadFunnoAsDownload(String functrlno);
    Resource loadFunnoAsDownload(String functrlno, String fileType);
    boolean loadFunnoAsDownloadCheck(String functrlno);
    Resource loadCarAsDownload(String filename);
    boolean sendFileCustomer(MultipartFile file, String cellPhone);
    Resource loadCemeDownload(String filename);
    Resource loadRotateDownload(String fileDiretory, String filename);
    //boolean deleteUploadFile(String fileName);

}
