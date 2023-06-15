package kr.co.yedaham.tablet.restserver.restserver.model.mourning;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class MourningId implements Serializable {
    @Column(name = "seq")
    private Integer seq;
    @Column(name = "fun_ctrl_no")
    private String funCtrlNo;

    public MourningId() {

    }

    public MourningId(Integer seq, String funCtrlNo) {
        this.seq = seq;
        this.funCtrlNo = funCtrlNo;
    }
}
