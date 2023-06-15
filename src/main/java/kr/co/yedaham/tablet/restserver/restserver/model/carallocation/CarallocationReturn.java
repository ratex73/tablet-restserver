package kr.co.yedaham.tablet.restserver.restserver.model.carallocation;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class CarallocationReturn {
    private long seq;
    private String funCtrlNo;
    private String xender;
    private String item;
    private int sceneYedahamCount;
    private int sceneOuterCount;
    private int deliveryYedahamCount;
    private int deliveryOuterCount;
    private int etcYedahamCount;
    private int etcOuterCount;
    private String type;

}
