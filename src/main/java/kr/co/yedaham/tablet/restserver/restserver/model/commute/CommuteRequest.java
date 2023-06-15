package kr.co.yedaham.tablet.restserver.restserver.model.commute;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class CommuteRequest {

    private String funCtrlNo;
    private String userId;
    private String funNm;
    private String fromDate;
    private String toDate;
    private String role;
    private List<String> deptCodes;
    private String userNm;
}
