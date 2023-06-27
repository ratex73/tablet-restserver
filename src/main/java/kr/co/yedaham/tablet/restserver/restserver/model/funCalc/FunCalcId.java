package kr.co.yedaham.tablet.restserver.restserver.model.funCalc;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@RequiredArgsConstructor
public class FunCalcId implements Serializable {

    @Column(name="FUN_CTRL_NO")
    private String funCtrlNo;

    @Column(name="FUN_BAL_CNT")
    private int funBalCnt;
}
