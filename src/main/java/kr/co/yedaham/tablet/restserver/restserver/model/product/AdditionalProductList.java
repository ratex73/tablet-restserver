package kr.co.yedaham.tablet.restserver.restserver.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.ColumnResult;

@AllArgsConstructor
@Data
public class AdditionalProductList {
    private String assiProdCd;
    private String mainGb;
    private String refalph;
    private String refnum;
    private String cd;
    private String cdnm;
    private String plinm;
    private String commt;
    private String qty;
    private String contQty;
    private String amt;
    private String state;
    private String creatYn;
    private String fu04Qty;
    private String fu04Amt;
}
