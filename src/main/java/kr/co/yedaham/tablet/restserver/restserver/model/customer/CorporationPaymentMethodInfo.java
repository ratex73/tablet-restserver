package kr.co.yedaham.tablet.restserver.restserver.model.customer;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CorporationPaymentMethodInfo {
    private String seq;
    private String custid;
    private String pymtMeth;
    private String etc;
}
