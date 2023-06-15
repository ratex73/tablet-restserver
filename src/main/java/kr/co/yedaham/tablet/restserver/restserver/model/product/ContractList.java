package kr.co.yedaham.tablet.restserver.restserver.model.product;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
public class ContractList {
    private String certno;
    private String refalph;
    private String refnum;
    private String cd;
    private String cdnm;
    private String plinm;
    private String commt;
    private String qty;
    private String amt;
    private String payback;
    private String maingb;
    private String init;
    private String upsellyn;
    private String assiProdCd;
}
