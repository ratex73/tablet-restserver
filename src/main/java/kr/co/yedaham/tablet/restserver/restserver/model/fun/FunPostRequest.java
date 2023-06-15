package kr.co.yedaham.tablet.restserver.restserver.model.fun;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.ArrayList;

@AllArgsConstructor
@Data
public class FunPostRequest {
    private String funStartDate;
    private String funEndDate;
    private String userid;
    private String funnm;
    private String funno;
    private String role;
    private ArrayList<Integer> status;
    private String deadNm;
    private String custNm;
    private String fdNm;
}
