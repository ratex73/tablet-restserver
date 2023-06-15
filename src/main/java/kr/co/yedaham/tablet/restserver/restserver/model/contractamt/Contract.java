package kr.co.yedaham.tablet.restserver.restserver.model.contractamt;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Contract {
    private String certNo;
    private String prodNm;
    private String fnrTypeNm;
    private String subsdt;
    private String contdt;
    private String contstNm;
    private String subsCustNm;
    private String custNm;
    private String totPyamt;
    private String pyamt;
    private String disAmt;
    private String pyclNm;
    private String pyprdNm;
    private String pycnt;
    private String lstPyym;
    private String cfDnm;
    private String disNm;
    private String pystNm;
    private String dlPyamt;
    private String sumPyamt;
    private String pymtMethNm;
    private String pymtBankNm;
    private String pymtAccNo;
    private String accHold;
    private String accReat;
    private String transDateNm;
    private String chDate;
    private String retDate;
    private String fnrDate;
}
