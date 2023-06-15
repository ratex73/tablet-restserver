package kr.co.yedaham.tablet.restserver.restserver.model.customer;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CorporationDisposableInfo {
    private String seq;
    private String custid;
    private String dispoproduct;
    private String delivery;
    private String etc;
}
