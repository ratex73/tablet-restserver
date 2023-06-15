package kr.co.yedaham.tablet.restserver.restserver.model.product;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ContractDetailList {

    private String certNo;
    private String state;
    private String stateNm;
    private String prodNm;
    private String contDate;
    private String funStartDate;
    private String funRegDate;
}
