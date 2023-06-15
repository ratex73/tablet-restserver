package kr.co.yedaham.tablet.restserver.restserver.entity;

import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.CarallocationProductInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.CarallocationRentalInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.fun.CarallocationInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.fun.CarallocationReturnInfo;
import lombok.Data;

import javax.persistence.*;


@SqlResultSetMapping(
        name = "CarallocationListMapping",
        classes = @ConstructorResult(
                targetClass = CarallocationInfo.class,
                columns = {
                        @ColumnResult(name = "FUN_START_DATE", type = String.class),
                        @ColumnResult(name = "DEPT_NM", type = String.class),
                        @ColumnResult(name = "EMP_NM", type = String.class),
                        @ColumnResult(name = "FUN_NM", type = String.class),
                        @ColumnResult(name = "FUN_DAY", type = String.class),
                        @ColumnResult(name = "FUN_TIME", type = String.class),
                        @ColumnResult(name = "HOPE_DAY", type = String.class),
                        @ColumnResult(name = "HOPE_TIME", type = String.class),
                        @ColumnResult(name = "DRIVER_NAME", type = String.class),
                        @ColumnResult(name = "RMK", type = String.class),
                        @ColumnResult(name = "DELAYTIME", type = String.class),
                        @ColumnResult(name = "GUBUN", type = String.class),
                        @ColumnResult(name = "ARRIVE_DAY", type = String.class),
                        @ColumnResult(name = "ARRIVE_TIME", type = String.class),
                        @ColumnResult(name = "LINE", type = int.class),
                        @ColumnResult(name = "CAR_NUM", type = String.class),
                        @ColumnResult(name = "TEL_NUM", type = String.class),
                        @ColumnResult(name = "ITEM_CK_NO", type = String.class),
                        @ColumnResult(name = "CAR_GUBUN", type = String.class),
                        @ColumnResult(name = "ALLOCATION_GUBUN", type = String.class),
                        @ColumnResult(name = "SIGNATURE", type = String.class),
                        @ColumnResult(name = "SEND_TIME", type = String.class),
                })
)

@NamedNativeQuery(name = "getCarallocationList",
        query = " select \n" +
                "       a.fun_start_date, -- 의전발생일\n" +
                "       c.desc1 dept_nm,  -- 관리지부명\n" +
                "       REPLACE(d.empnm1, SUBSTR(d.empnm1,2,1),'*') emp_nm,  -- 팀장(FD)\n" +
                "       e.fun_nm,         -- 장례식장 업체명\n" +
                "       b.f_day as fun_day,          -- 배차접수일\n" +
                "       b.f_time as fun_time,         -- 배차접수시간\n" +
                "       b.t_day as hope_day,          -- 배차희망일\n" +
                "       b.t_time as hope_time,         -- 배차희망시간\n" +
                "       REPLACE(b.empnm, SUBSTR(b.empnm,2,1),'*') driver_name,    -- 배정기사명\n" +
                "       b.rmk,            -- 특이사항(비고)\n" +
                "       b.m_value as delaytime,        -- 지연시간(분)\n" +
                "       b.gubun,          -- 차량구분 ( 몇호차 )\n" +
                "       b.r_day as arrive_day,          -- 실제도착날짜\n" +
                "       b.r_time as arrive_time,         -- 실제도착시간\n" +
                "       b.line,           -- 라인\n" +
                "       b.car_num,        -- 차량번호 \n" +
                "       b.tel_num,        -- 배정기사 전화번호\n" +
                "       f.item_ck_no,     \n" +
                "       decode(b.car_type,'1','자차','2','외주','3','내부') car_gubun, -- 차량구분(자차/외주/내부),\n" +
                "       decode(b.delivery_type,'00','신규','01','추가','02','시간변경(FD)','03','시간변경(물류실장)','04','취소','') allocation_gubun,\n" +
                "       b.signature,\n" +
                "       b.send_time \n" +
                "from tbfu1001 a\n" +
                "inner      join ch012d   b on a.fun_ctrl_no = b.fun_ctrl_no\n" +
                "left outer join hb001m   c on a.manag_branch = c.dept\n" +
                "left outer join hr001m   d on a.leader_id = d.empno\n" +
                "left outer join tbcm1005 e on a.fun_cd = e.fun_cd\n" +
                "left outer join ch013m   f on a.fun_ctrl_no = f.fun_ctrl_no and b.line = f.high_line\n" +
                "where a.fun_ctrl_no = :functrlno\n" +
                "order by b.line desc"
        ,
        resultClass = CarallocationSeqEntity.class,
        resultSetMapping = "CarallocationListMapping")

@SqlResultSetMapping(
        name = "CarallocationReturnListMapping",
        classes = @ConstructorResult(
                targetClass = CarallocationReturnInfo.class,
                columns = {
                        @ColumnResult(name = "ITEM", type = String.class),
                        @ColumnResult(name = "ITEM_NM", type = String.class),
                        @ColumnResult(name = "AMOUNT_QTY", type = String.class),
                        @ColumnResult(name = "RETURN_AMOUNT", type = String.class),
                        @ColumnResult(name = "NO_RETURN_AMOUNT", type = String.class)
                })
)

@NamedNativeQuery(name = "getCarallocationReturnList",
        query = " select item,                                              -- 품목코드\n" +
                "       item_nm,                                           -- 품목명\n" +
                "       amount_qty,                                        -- 반납대상\n" +
                "       return_amount,                                     -- 반납확인\n" +
                "       (amount_qty-nvl(return_amount,0)) no_return_amount -- 미반납\n" +
                "from\n" +
                "(\n" +
                " select a.item,\n" +
                "        a.desc1 item_nm,\n" +
                "        (select sum(amount) from ch013d where fun_ctrl_no = :functrlno and item = a.item and ck_no_type = '1') amount_qty,\n" +
                "        (select sum(amount) from ch013d where fun_ctrl_no = :functrlno and item = a.item and ck_no_type = '2') return_amount\n" +
                " from sb012m a\n" +
                " left outer join ch013d b on a.item = b.item\n" +
                " where a.item in(select item from sb012m where prdline = '150' and use_yn = 'Y' and plnorder = 'Y')\n" +
                " group by a.item,a.desc1\n" +
                ")\n" +
                "order by item"
        ,
        resultClass = CarallocationSeqEntity.class,
        resultSetMapping = "CarallocationReturnListMapping")

@SqlResultSetMapping(
        name = "CarallocationProductInfoMapping",
        classes = @ConstructorResult(
                targetClass = CarallocationProductInfo.class,
                columns = {
                        @ColumnResult(name = "divcd", type = String.class),
                        @ColumnResult(name = "item", type = String.class),
                        @ColumnResult(name = "itemnm", type = String.class),
                        @ColumnResult(name = "tablet_seq", type = long.class),
                })
)

@NamedNativeQuery(name = "getCarallocationProductInfo",
        query = "select divcd,item,desc1 as itemnm,tablet_seq\n" +
                "from sb012m\n" +
                "where use_yn = 'Y'\n" +
                "and tablet_seq <> '9999'\n"
        ,
        resultClass = CarallocationSeqEntity.class,
        resultSetMapping = "CarallocationProductInfoMapping"
)

@SqlResultSetMapping(
        name = "CarallocationLentalListMapping",
        classes = @ConstructorResult(
                targetClass = CarallocationRentalInfo.class,
                columns = {
                        @ColumnResult(name = "seq", type = Long.class),
                        @ColumnResult(name = "line", type = String.class),
                        @ColumnResult(name = "item", type = String.class),
                        @ColumnResult(name = "itemnm", type = String.class),
                        @ColumnResult(name = "amount", type = String.class),
                })
)

@NamedNativeQuery(name = "getCarallocationLentalList",
        query = "select a.seq,\n" +
                "       a.line,\n" +
                "       a.item,\n" +
                "       b.desc1 itemnm,\n" +
                "       amount\n" +
                "from ch013d a\n" +
                "left outer join sb012m b on a.item = b.item\n" +
                "where a.fun_ctrl_no = :funno\n" +
                "and   a.item_ck_no = :itemno\n" +
                "and   a.ck_no_type = '1' ORDER BY SEQ DESC"
        ,
        resultClass = CarallocationSeqEntity.class,
        resultSetMapping = "CarallocationLentalListMapping"
)

@Data
@Entity
@Table(name="ch014d", schema = "tk_bodev")
public class CarallocationSeqEntity {
    @Id
    private String seq;
}