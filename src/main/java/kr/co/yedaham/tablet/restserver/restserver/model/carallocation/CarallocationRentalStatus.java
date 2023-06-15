package kr.co.yedaham.tablet.restserver.restserver.model.carallocation;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CarallocationRentalStatus {
    private String funno;
    private String gender;
    private String item;
    private int yedaham;
    private int outsourcing;
    private int total;
    private int reaminCount;
}