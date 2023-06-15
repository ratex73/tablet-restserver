package kr.co.yedaham.tablet.restserver.restserver.model.carallocation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
@Setter
public class CarallocationRental {
    private long seq;
    private String line;
    private String funCtrlNo;
    private String itemCkNo;
    private String gubun;
    private String item;
    private long amount;
    private String rentalDate;
    private long highLine;
    private String dept;
    private String ckNoType;
}
