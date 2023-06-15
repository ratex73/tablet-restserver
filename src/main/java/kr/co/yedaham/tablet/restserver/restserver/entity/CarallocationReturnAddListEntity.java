package kr.co.yedaham.tablet.restserver.restserver.entity;

import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.CarallocationReturn;
import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.CarallocationReturnAdd;
import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.CarallocationReturnProduct;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@SqlResultSetMapping(
        name = "ReturnAddInfoMapping",
        classes = @ConstructorResult(
                targetClass = CarallocationReturnAdd.class,
                columns = {
                        @ColumnResult(name = "sceneYedahamCount", type = Integer.class),
                        @ColumnResult(name = "sceneOuterCount", type = Integer.class),
                        @ColumnResult(name = "deliveryYedahamCount", type = Integer.class),
                        @ColumnResult(name = "deliveryOuterCount", type = Integer.class),
                        @ColumnResult(name = "etcYedahamCount", type = Integer.class),
                        @ColumnResult(name = "etcOuterCount", type = Integer.class)
                })
)


@NamedNativeQuery(name = "findReturnAddInfoMapping",
        query = "select \n" +
                "nvl(SUM(SCENE_YEDAHAM_COUNT),0) sceneYedahamCount, \n" +
                "nvl(SUM(SCENE_OUTER_COUNT),0) sceneOuterCount, \n" +
                "nvl(SUM(DELIVERY_YEDAHAM_COUNT),0) deliveryYedahamCount, \n" +
                "nvl(SUM(DELIVERY_OUTER_COUNT),0) deliveryOuterCount, \n" +
                "nvl(SUM(ETC_YEDAHAM_COUNT),0) etcYedahamCount, \n" +
                "nvl(SUM(ETC_OUTER_COUNT),0) etcOuterCount \n" +
                "from add_ch013d \n" +
                "where fun_ctrl_no = :funno\n" +
                "and type = :type\n" ,
        resultClass = CarallocationReturnAddListEntity.class,
        resultSetMapping = "ReturnAddInfoMapping")


@Entity
@Getter
@Setter
@Table(name="add_ch013d", schema = "tk_bodev")
@NoArgsConstructor
public class CarallocationReturnAddListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADD_CH013D_SEQ")
    @SequenceGenerator(sequenceName = "ADD_CH013D_SEQ", allocationSize = 1, name = "ADD_CH013D_SEQ")
    private long seq;
    @NotNull
    private String funCtrlNo;
    @NotNull
    private String xender;
    @NotNull
    private String item;
    private int sceneYedahamCount;
    private int sceneOuterCount;
    private int deliveryYedahamCount;
    private int deliveryOuterCount;
    private int etcYedahamCount;
    private int etcOuterCount;
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_return_id")
    private CarallocationReturnEntity carReturn; //주문 회원

    @Builder
    public CarallocationReturnAddListEntity(CarallocationReturn carallocationReturn) {
        this.seq = carallocationReturn.getSeq();
        this.funCtrlNo = carallocationReturn.getFunCtrlNo();
        this.xender = carallocationReturn.getXender();
        this.item = carallocationReturn.getItem();
        this.sceneYedahamCount = carallocationReturn.getSceneYedahamCount();
        this.sceneOuterCount = carallocationReturn.getSceneOuterCount();
        this.deliveryYedahamCount = carallocationReturn.getDeliveryYedahamCount();
        this.deliveryOuterCount = carallocationReturn.getDeliveryOuterCount();
        this.etcYedahamCount = carallocationReturn.getEtcYedahamCount();
        this.etcOuterCount = carallocationReturn.getEtcOuterCount();
        this.type = carallocationReturn.getType();
    }
}
