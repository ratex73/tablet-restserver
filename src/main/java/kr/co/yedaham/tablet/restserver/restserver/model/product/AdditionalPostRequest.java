package kr.co.yedaham.tablet.restserver.restserver.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AdditionalPostRequest {
    private String certno;
    private String grpcode;
    private String amt;
    private String cdnm;
    private String plinm;
    private String plicd;
    private String plinm1;
    private String plinm2;
    private String plinm3;

}
