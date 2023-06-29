package kr.co.yedaham.tablet.restserver.restserver.entity;


import kr.co.yedaham.tablet.restserver.restserver.model.file.FileSiginRequest;
import kr.co.yedaham.tablet.restserver.restserver.model.file.FileSignin;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;



@DynamicUpdate
@Data
@Entity
@NoArgsConstructor
@Getter
@ToString
@Table(name="TBFU4435", schema = "tk_fsdev")
public class FunSigninEntity {
    @EmbeddedId
    private FileSignin fileSignin;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_PATH")
    private String filePath;

    @Column(name = "REG_DATE")
    private LocalDateTime regDate;

    @Column(name = "REG_ID")
    private String regId;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "UPDATE_ID")
    private String updateId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "CUST_REF")
    private String custRef;

    @Column(name = "MOURNER")
    private String mourner;

    @Column(name = "CUST")
    private String cust;

    @Column(name = "FILE_SRC")
    private String fileSrc;

    @Builder
    public FunSigninEntity(FileSiginRequest fileSiginRequest) {
        this.fileSignin = fileSiginRequest.getFileSignin();
        this.fileName = fileSiginRequest.getFileName();
        this.filePath = fileSiginRequest.getFilePath();
        this.regDate = fileSiginRequest.getRegDate();
        this.regId =fileSiginRequest.getRegId();
        this.name = fileSiginRequest.getCustName();
        this.phone = fileSiginRequest.getCustPhone();
        this.custRef = fileSiginRequest.getCustRef();
        this.mourner = fileSiginRequest.getMourner();
        this.fileSrc = fileSiginRequest.getFileSrc();
    }

}
