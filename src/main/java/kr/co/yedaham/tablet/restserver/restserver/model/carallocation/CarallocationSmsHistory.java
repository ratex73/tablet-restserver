package kr.co.yedaham.tablet.restserver.restserver.model.carallocation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
@Setter
public class CarallocationSmsHistory {
    private String funCtrlNo;
    private String itemCkNo;
    private String gubun;
    private int highLine;
    private String lastLmsTime;

}
