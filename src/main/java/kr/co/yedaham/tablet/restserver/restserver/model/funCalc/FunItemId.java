package kr.co.yedaham.tablet.restserver.restserver.model.funCalc;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@RequiredArgsConstructor
//TBFU1004 PK
public class FunItemId implements Serializable {

    @Column(name="FUN_CTRL_NO")
    private String funCtrlNo;

    @Column(name="FUN_BAL_CNT")
    private Integer  funBalCnt;

    @Column(name="MAIN_GB")
    private String mainGb;

    @Column(name="ASSI_PROD_CD")
    private String assiProdCd;
}
