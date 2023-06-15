package kr.co.yedaham.tablet.restserver.restserver.model;

import lombok.Data;

@Data
public class ProductPurchase {
    private String custId;
    private String birthDate;
    private String custNm;
    private String cellPhone;
    private String contDate;
    private int sex;
}
