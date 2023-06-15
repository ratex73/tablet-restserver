package kr.co.yedaham.tablet.restserver.restserver.model.customer;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CorporationInfo {
    private String seq;
    private String prodNm;
    private String prodAmt;
    private String position;
}
