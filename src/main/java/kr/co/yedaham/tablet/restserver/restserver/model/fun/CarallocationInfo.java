package kr.co.yedaham.tablet.restserver.restserver.model.fun;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CarallocationInfo {
    private String funStartDate;
    private String deptNm;
    private String empNm;
    private String funNm;
    private String funDay;
    private String funTime;
    private String hopeDay;
    private String hopeTime;
    private String driverName;
    private String rmk;
    private String delaytime;
    private String gubun;
    private String arriveDay;
    private String arriveTime;
    private int line;
    private String carNum;
    private String telNum;
    private String itemCkNo;
    private String carGubun;
    private String allocationGubun;
    private String signature;
    private String sendTime;
}
