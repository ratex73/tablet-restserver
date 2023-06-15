package kr.co.yedaham.tablet.restserver.restserver.model.carallocation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CarallocationRentalRequest {
    private String funno;
    private String itemno;
}