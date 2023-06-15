package kr.co.yedaham.tablet.restserver.restserver.model.carallocation;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class CarallocationReturnProduct {
    private String carReturnId;
    private String empnm;
    private String regdate;
    private String signature;
}