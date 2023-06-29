package kr.co.yedaham.tablet.restserver.restserver.model.funCalc;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@RequiredArgsConstructor
public class FunPaybackInfoId implements Serializable {

    @Column(name="TRAN_DATE")
    private String tranDate; //발생일자

    @Column(name="CERT_NO")
    private String certNo; //증서번호

    @Column(name="REF_METH")
    private String refMeth; //환급방법
}
