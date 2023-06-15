package kr.co.yedaham.tablet.restserver.restserver.entity;

import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.CarallocationId;
import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.CarallocationSaveInfo;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@DynamicUpdate
@Table(name="ch012d", schema = "tk_bodev")
public class CarallocationInfoEntity {
    @EmbeddedId
    private CarallocationId carallocationId;
    @Column(name = "F_DAY")
    private String registerDay;
    @Column(name = "F_TIME")
    private String registerTime;
    @Column(name = "T_DAY")
    private String hopeDay;
    @Column(name = "T_TIME")
    private String hopeTime;
    @Column(name = "R_DAY")
    private String arriveDay;
    @Column(name = "R_TIME")
    private String arriveTime;
    @Column(name = "DELIVERY_TYPE")
    private String deliveryType;
    @Column(name = "SIGNATURE")
    private String signature;

    @Builder
    public CarallocationInfoEntity(CarallocationSaveInfo carallocationSaveInfo) {
        this.carallocationId = carallocationSaveInfo.getCarallocationId();
        this.registerDay = carallocationSaveInfo.getRegisterDay();
        this.registerTime = carallocationSaveInfo.getRegisterTime();
        this.hopeDay = carallocationSaveInfo.getHopeDay();
        this.hopeTime = carallocationSaveInfo.getHopeTime();
        this.arriveDay = carallocationSaveInfo.getArriveDay();
        this.arriveTime = carallocationSaveInfo.getArriveTime();
        this.deliveryType = carallocationSaveInfo.getDeliveryType();
        this.signature = carallocationSaveInfo.getSignature();
    }
}
