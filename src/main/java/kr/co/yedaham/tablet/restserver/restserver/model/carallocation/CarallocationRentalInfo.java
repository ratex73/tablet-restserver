package kr.co.yedaham.tablet.restserver.restserver.model.carallocation;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CarallocationRentalInfo {
    private long seq;
    private String line;
    private String item;
    private String itemnm;
    private String amount;
}
