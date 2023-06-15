package kr.co.yedaham.tablet.restserver.restserver.model.carallocation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@RequiredArgsConstructor
public class CarallocationId implements Serializable  {

    @Column(name = "line")
    private int line;
    @Column(name = "fun_ctrl_no")
    private String funCtrlNo;
}
