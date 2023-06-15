package kr.co.yedaham.tablet.restserver.restserver.model.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CorporationRangeProductSupportInfo {
    private String seq;
    private String custid;
    private String range;
    private String grparents;
    private String parents;
    private String own;
    private String partner;
    private String sigrparents;
    private String siparents;
    private String brother;
    private String childeren;
    private String grandson;
}