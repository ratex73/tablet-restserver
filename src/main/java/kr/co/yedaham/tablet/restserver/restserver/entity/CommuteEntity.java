package kr.co.yedaham.tablet.restserver.restserver.entity;


import kr.co.yedaham.tablet.restserver.restserver.model.commute.CommuteCancelInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.commute.CommuteFdInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.commute.CommuteSaveInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.commute.DeptInfo;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;



@SqlResultSetMapping(
        name = "getCommuteFdListMapping",
        classes = @ConstructorResult(
                targetClass = CommuteFdInfo.class,
                columns = {
                        @ColumnResult(name = "SEQ", type = Long.class),
                        @ColumnResult(name = "FUN_CTRL_NO", type = String.class),
                        @ColumnResult(name = "FUN_DAYS", type = String.class),
                        @ColumnResult(name = "START_DATE", type = String.class),
                        @ColumnResult(name = "START_TIME", type = String.class),
                        @ColumnResult(name = "END_DATE", type = String.class),
                        @ColumnResult(name = "END_TIME", type = String.class),
                        @ColumnResult(name = "FD_NAME", type = String.class),
                        @ColumnResult(name = "FD_ID", type = String.class),
                })
)




@NamedNativeQuery(name = "getCommuteFdList",
        query = "SELECT " +
                "SEQ,\n" +
                "FUN_CTRL_NO,\n" +
                "FUN_DAYS,\n" +
                "START_DATE,\n" +
                "START_TIME,\n" +
                "END_DATE,\n" +
                "END_TIME,\n" +
                "NVL(B.EMPNM1,C.EMP_NAME) AS FD_NAME,\n" +
                "A.USER_ID AS FD_ID\n" +
                "FROM YDHRCH010M A LEFT JOIN HR001M B\n" +
                "                         ON A.USER_ID = B.EMPNO\n" +
                "                  LEFT JOIN TBFU4004 C\n" +
                "                         ON C.MFS_USERID = A.USER_ID\n" +
                "WHERE FUN_CTRL_NO =:funCtrlNo\n" +
                "ORDER BY FUN_DAYS DESC, USER_ID",
        resultClass = CommuteEntity.class,
        resultSetMapping = "getCommuteFdListMapping")



@SqlResultSetMapping(
        name = "getCommuteCancelListMapping",
        classes = @ConstructorResult(
                targetClass = CommuteCancelInfo.class,
                columns = {
                        @ColumnResult(name = "SEQ", type = Long.class),
                        @ColumnResult(name = "FUN_CTRL_NO", type = String.class),
                        @ColumnResult(name = "FUN_DAYS", type = String.class),
                        @ColumnResult(name = "START_DATE", type = String.class),
                        @ColumnResult(name = "START_TIME", type = String.class),
                        @ColumnResult(name = "END_DATE", type = String.class),
                        @ColumnResult(name = "END_TIME", type = String.class),
                })
)


@NamedNativeQuery(name = "getCommuteCancelList",
        query = "SELECT " +
                "SEQ,\n" +
                "FUN_CTRL_NO,\n" +
                "FUN_DAYS,\n" +
                "START_DATE,\n" +
                "START_TIME,\n" +
                "END_DATE,\n" +
                "END_TIME\n" +
                "FROM YDHRCH010M \n" +
                "WHERE USER_ID =:userId\n" +
                "ORDER BY START_DATE DESC, START_TIME DESC",
        resultClass = CommuteEntity.class,
        resultSetMapping = "getCommuteCancelListMapping")


@SqlResultSetMapping(
        name = "getDeptListMapping",
        classes = @ConstructorResult(
                targetClass = DeptInfo.class,
                columns = {
                        @ColumnResult(name = "userNo", type = String.class),
                        @ColumnResult(name = "userName", type = String.class),
                        @ColumnResult(name = "deptNo", type = String.class),
                        @ColumnResult(name = "deptName", type = String.class),
                })
)


@NamedNativeQuery(name = "getDeptList",
        query = "SELECT " +
                "A.EMPNO as userNo,\n" +
                "A.EMPNM1 as userName,\n" +
                "B.DEPT AS deptNo,\n" +
                "B.DESC1 AS deptName\n" +
                "FROM HR001M A\n" +
                "LEFT  JOIN HB001M B ON A.DEPT = B.DEPT\n" +
                "WHERE A.DEPT = (SELECT DEPT FROM HR001M WHERE EMPNO =:userId)\n" +
                "AND  A.RETDT is null\n" +
                "ORDER BY A.EMPNO",
        resultClass = CommuteEntity.class,
        resultSetMapping = "getDeptListMapping")




@Data
@Entity
@NoArgsConstructor
@Getter
@ToString
@Table(name="YDHRCH010M", schema = "tk_bodev")
public class CommuteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMUTE_SEQ")
    @SequenceGenerator(sequenceName = "COMMUTE_SEQ", allocationSize = 1, name = "COMMUTE_SEQ")
    @Column(name = "SEQ")
    private long seq;
    @Column(name = "FUN_CTRL_NO")
    private String funCtrlNo;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "START_DATE")
    private String startDate;
    @Column(name = "END_DATE")
    private String endDate;
    @Column(name = "START_TIME")
    private String startTime;
    @Column(name = "END_TIME")
    private String endTime;
    @Column(name = "START_LAT")
    private BigDecimal startLat;
    @Column(name = "START_LNG")
    private BigDecimal startLng;
    @Column(name = "END_LAT")
    private BigDecimal endLat;
    @Column(name = "END_LNG")
    private BigDecimal endLng;
    @Column(name = "FUN_DAYS")
    private String funDays;
    @Column(name = "START_DIS")
    private BigDecimal startDis;
    @Column(name = "END_DIS")
    private BigDecimal endDis;

    @Builder
    public CommuteEntity(CommuteSaveInfo commuteSaveInfo) {
        this.seq = commuteSaveInfo.getSeq();
        this.funCtrlNo = commuteSaveInfo.getFunCtrlNo();
        this.userId = commuteSaveInfo.getUserId();
        this.startDate = commuteSaveInfo.getStartDate();
        this.endDate = commuteSaveInfo.getEndDate();
        this.startTime = commuteSaveInfo.getStartTime();
        this.endTime = commuteSaveInfo.getEndTime();
        this.startLat = commuteSaveInfo.getStartLat();
        this.startLng = commuteSaveInfo.getStartLng();
        this.endLat = commuteSaveInfo.getEndLat();
        this.endLng = commuteSaveInfo.getEndLng();
        this.funDays = commuteSaveInfo.getFunDays();
        this.startDis = commuteSaveInfo.getStartDis();
        this.endDis = commuteSaveInfo.getEndDis();
    }
}
