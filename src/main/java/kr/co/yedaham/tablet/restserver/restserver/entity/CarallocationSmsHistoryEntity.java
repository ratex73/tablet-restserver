package kr.co.yedaham.tablet.restserver.restserver.entity;

import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.CarallocationSmsHistory;
import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name="ch013m", schema = "tk_bodev")
public class CarallocationSmsHistoryEntity {
    private String funCtrlNo;
    @Id
    private String itemCkNo;
    private String gubun;
    private int highLine;

    @Builder
    public CarallocationSmsHistoryEntity(CarallocationSmsHistory carallocationSmsHistory) {
        Assert.hasText(carallocationSmsHistory.getFunCtrlNo(), "funCtrlNo must not be empty");
        Assert.hasText(carallocationSmsHistory.getItemCkNo(), "itemCkNo must not be empty");
        this.funCtrlNo = carallocationSmsHistory.getFunCtrlNo();
        this.itemCkNo = carallocationSmsHistory.getItemCkNo();
        this.highLine = carallocationSmsHistory.getHighLine();
    }
}