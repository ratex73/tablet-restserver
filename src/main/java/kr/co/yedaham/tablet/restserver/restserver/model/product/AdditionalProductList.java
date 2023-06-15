package kr.co.yedaham.tablet.restserver.restserver.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AdditionalProductList {
    private String refalph;
    private String refnum;
    private String cd;
    private String cdnm;
    private String plinm;
    private String commt;
    private String qty;
    private String amt;
}
