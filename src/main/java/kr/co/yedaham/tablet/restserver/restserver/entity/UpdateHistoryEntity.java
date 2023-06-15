package kr.co.yedaham.tablet.restserver.restserver.entity;

import kr.co.yedaham.tablet.restserver.restserver.model.comm.FunCoComList;
import kr.co.yedaham.tablet.restserver.restserver.model.mourning.MourningInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.updatehistory.UpdateHistoryList;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@SqlResultSetMapping(
        name = "UpdateHistoryListMapping",
        classes = @ConstructorResult(
                targetClass = UpdateHistoryList.class,
                columns = {
                        @ColumnResult(name = "SEQ", type = long.class),
                        @ColumnResult(name = "UPDATE_INFO", type = String.class),
                        @ColumnResult(name = "FUN_CTRL_NO", type = String.class),
                        @ColumnResult(name = "REG_DATE", type = String.class),
                        @ColumnResult(name = "REG_ID", type = String.class)
                })
)


@NamedNativeQuery(name = "findUpdateHistoryList",
        query = "  SELECT                                                                                                     \n" +
                "        SEQ                                                                                          \n" +
                "      , UPDATE_INFO                                                                                          \n" +
                "      , FUN_CTRL_NO                                                                                          \n" +
                "      , TO_CHAR(REG_DATE, 'YYYY-MM-DD HH24:MI:SS') REG_DATE                                             \n" +
                "      , REG_ID                                                                                          \n" +
                "  FROM                                                                                                       \n" +
                "    TBFU4474                                                                                                 \n" +
                "  WHERE 1=1                                                                                                  \n" +
                "    AND REG_ID = :updateid AND FUN_CTRL_NO = :funno                                                       \n" +
                "    AND REG_DATE >= add_months(sysdate, -3)                                                                  \n" +
                "  ORDER BY REG_DATE DESC, SEQ                                                                                    "
        ,
        resultClass = UpdateHistoryList.class,
        resultSetMapping = "UpdateHistoryListMapping")


@Data
@Entity
@Getter
@Table(name="TBFU4474", schema = "tk_fsdev")
public class UpdateHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUDIT_SEQ")
    @SequenceGenerator(sequenceName = "AUDIT_SEQ", allocationSize = 1, name = "AUDIT_SEQ")
    private long seq;
    private String updateInfo;
    private String funCtrlNo;
    private LocalDateTime regDate;
    private String regId;

    @PrePersist
    public void createdAt() {
        this.regDate = LocalDateTime.now();
    }

    @Builder
    public UpdateHistoryEntity(UpdateHistoryList updateHistoryList) {
        this.updateInfo = updateHistoryList.getUpdateInfo();
        this.funCtrlNo = updateHistoryList.getFunctrlno();
        this.regId = updateHistoryList.getRegId();
    }
}
