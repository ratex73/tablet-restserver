package kr.co.yedaham.tablet.restserver.restserver.entity;

import io.jsonwebtoken.lang.Assert;
import kr.co.yedaham.tablet.restserver.restserver.model.base.BaseTimeEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.mourning.MourningId;
import kr.co.yedaham.tablet.restserver.restserver.model.mourning.MourningInfo;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@SqlResultSetMapping(
        name = "MourningMaxSeqMapping",
        classes = @ConstructorResult(
                targetClass = Integer.class,
                columns = {
                        @ColumnResult(name = "SEQ", type = Integer.class),
                })
)

@NamedNativeQuery(name = "findMaxSeq",
        query = " SELECT NVL(MAX(SEQ),0) + 1 AS SEQ FROM TBFU4462 WHERE FUN_CTRL_NO = :funno \n "
        ,
        resultClass = MourningEntity.class,
        resultSetMapping = "MourningMaxSeqMapping")

@DynamicUpdate
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="TBFU4462", schema = "tk_fsdev")
@ToString
public class MourningEntity extends BaseTimeEntity {
    @EmbeddedId
    private MourningId mourningId;

    private String distbArrvHopeDate;
    private String distbRealArrvDate;
    private String distbArrvDiffMin;
    private String regId;
    private String updateId;
    private String distbDelayType;
    private String firstCallDt;
    private String mortuarySetDt;
    private String disposItemArrvDt;
    private String meetCounselDt;
    private char useYn;
    private char delYn;

    @Builder
    public MourningEntity(MourningInfo mourningInfo) {
        this.mourningId = mourningInfo.getMourningId();
        this.distbArrvHopeDate = mourningInfo.getDistbArrvHopeDate();
        this.distbRealArrvDate = mourningInfo.getDistbRealArrvDate();
        this.regId = mourningInfo.getRegId();
        this.updateId = mourningInfo.getUpdateId();
        this.distbDelayType = mourningInfo.getDistbDelayType();
        this.firstCallDt = mourningInfo.getFirstCallDt();
        this.mortuarySetDt = mourningInfo.getMortuarySetDt();
        this.disposItemArrvDt = mourningInfo.getDisposItemArrvDt();
        this.meetCounselDt = mourningInfo.getMeetCounselDt();
        this.useYn = 'Y';
        this.delYn = 'N';
    }
}
