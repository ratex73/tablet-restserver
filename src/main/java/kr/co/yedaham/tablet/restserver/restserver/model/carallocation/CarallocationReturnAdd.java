package kr.co.yedaham.tablet.restserver.restserver.model.carallocation;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CarallocationReturnAdd {
    private Integer sceneYedahamCount;
    private Integer sceneOuterCount;
    private Integer deliveryYedahamCount;
    private Integer deliveryOuterCount;
    private Integer etcYedahamCount;
    private Integer etcOuterCount;
}
