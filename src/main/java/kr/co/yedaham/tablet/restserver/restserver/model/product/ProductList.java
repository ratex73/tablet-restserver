package kr.co.yedaham.tablet.restserver.restserver.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductList {
    private String certno;
    private String prodCd;
    private String prodNm;
    private String amt;
    private String grpCd;
    private String qty;
    private String payback;
}
