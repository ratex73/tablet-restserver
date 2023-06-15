package kr.co.yedaham.tablet.restserver.restserver.model.carallocation;

import lombok.*;


@RequiredArgsConstructor
@ToString
@Getter
@Setter
public class CarallocationSaveInfo {
    private CarallocationId carallocationId;
    private String registerDay;
    private String registerTime;
    private String hopeDay;
    private String hopeTime;
    private String arriveDay;
    private String arriveTime;
    private String contents;
    private String deliveryType;
    private String signature;
}
