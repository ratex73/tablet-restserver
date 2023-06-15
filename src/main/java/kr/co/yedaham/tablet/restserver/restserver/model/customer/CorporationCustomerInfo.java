package kr.co.yedaham.tablet.restserver.restserver.model.customer;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CorporationCustomerInfo {
    private String seq;
    private String custid;
    private String name;
    private String tel;
    private String email;
}
