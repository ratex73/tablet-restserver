package kr.co.yedaham.tablet.restserver.restserver.model.file;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FileSiginRequest {
    private FileSignin fileSignin;
    private String fileName;
    private String filePath;
    private LocalDateTime regDate;
    private String regId;
    private String custName;
    private String custPhone;
    private String custRef;
    private String mourner;
    private String fileSrc;

}
