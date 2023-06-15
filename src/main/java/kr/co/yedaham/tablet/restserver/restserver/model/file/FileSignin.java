package kr.co.yedaham.tablet.restserver.restserver.model.file;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class FileSignin implements Serializable {

    @Column(name = "FUN_CTRL_NO")
    private String funCtrlNo;
    @Column(name = "FILE_TYPE")
    private String fileType;
    @Column(name = "FILE_SEQ")
    private String fileSeq;

}
