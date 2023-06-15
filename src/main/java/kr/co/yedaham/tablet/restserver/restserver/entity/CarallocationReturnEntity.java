package kr.co.yedaham.tablet.restserver.restserver.entity;

import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.CarallocationReturnProduct;
import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.CarallocationReturnSignature;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@SqlResultSetMapping(
        name = "ReturnProductListMapping",
        classes = @ConstructorResult(
                targetClass = CarallocationReturnProduct.class,
                columns = {
                        @ColumnResult(name = "car_return_id", type = String.class),
                        @ColumnResult(name = "empnm", type = String.class),
                        @ColumnResult(name = "regdate", type = String.class),
                        @ColumnResult(name = "signature", type = String.class),
                })
)
@NamedNativeQuery(name = "findReturnProductListMapping",
        query = "select c.car_return_id,\n" +
                "                       b.empnm1 empnm,\n" +
                "                       to_char(c.reg_date,'yyyy-mm-dd') regdate,\n" +
                "                       c.signature\n" +
                "from add_ch013d a\n" +
                "left outer join car_return c on a.CAR_RETURN_ID = c.CAR_RETURN_ID\n" +
                "left outer join hr001m b on c.reg_empno = b.empno\n" +
                "where a.fun_ctrl_no = :funno\n" +
                "group by a.fun_ctrl_no,c.CAR_RETURN_ID,b.empnm1,c.reg_date,c.signature\n" +
                "order by c.car_return_id desc",
        resultClass = CarallocationReturnEntity.class,
        resultSetMapping = "ReturnProductListMapping")

@Entity
@Getter
@Setter
@DynamicUpdate
@Table(name="CAR_RETURN", schema = "tk_bodev")
public class CarallocationReturnEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELIVER_SEQ")
    @SequenceGenerator(sequenceName = "DELIVER_SEQ", allocationSize = 1, name = "DELIVER_SEQ")
    @Column(name = "CAR_RETURN_ID")
    private long carReturnId;
    private String funCtrlNo;
    private String signature;
    @Column(name = "REG_EMPNO")
    private String regEmpno;
    @CreationTimestamp
    @Column(name = "REG_DATE")
    private LocalDateTime regDate;
    private String deliveryArea;
    private String etcMemo;

    @OneToMany(mappedBy = "carReturn",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarallocationReturnAddListEntity> addCh013ds = new ArrayList<>();

    @Builder
    public CarallocationReturnEntity(CarallocationReturnSignature returnSignature) {
        this.carReturnId = returnSignature.getCarReturnId();
        this.funCtrlNo = returnSignature.getFunCtrlNo();
        this.signature = returnSignature.getSignature();
        this.regEmpno = returnSignature.getRegEmpno();
        this.regDate = returnSignature.getRegDate();
        this.deliveryArea = returnSignature.getDeliveryArea();
        this.etcMemo = returnSignature.getEtcMemo();
    }

    public void addReturn(CarallocationReturnAddListEntity returnEntity) {
        addCh013ds.add(returnEntity);
        returnEntity.setCarReturn(this);
    }

}
