package kr.co.yedaham.tablet.restserver.restserver.model.carallocation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
@Setter
public class CarallocationSignatureRequest {
    private String funno;
    private int line;
    private String signature;
    private String arriveDay;
    private String arriveTime;
}
