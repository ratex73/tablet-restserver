package kr.co.yedaham.tablet.restserver.restserver.model.carallocation;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CarallocationProductInfo {
    private String divcd;
    private String item;
    private String itemnm;
    private long tablet_seq;
}
