package kr.co.yedaham.tablet.restserver.restserver.entity;

import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.CarallocationRental;
import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.CarallocationRentalStatus;
import kr.co.yedaham.tablet.restserver.restserver.model.product.AdditionalProductList;
import lombok.*;

import javax.persistence.*;

@SqlResultSetMapping(
        name = "RentalStatusListMapping",
        classes = @ConstructorResult(
                targetClass = CarallocationRentalStatus.class,
                columns = {
                        @ColumnResult(name = "funno", type = String.class),
                        @ColumnResult(name = "gender", type = String.class),
                        @ColumnResult(name = "item", type = String.class),
                        @ColumnResult(name = "yedaham", type = Integer.class),
                        @ColumnResult(name = "outsourcing", type = Integer.class),
                        @ColumnResult(name = "total", type = Integer.class),
                        @ColumnResult(name = "remainCount", type = Integer.class),
                })
)
@NamedNativeQuery(name = "findRentalStatusListMapping",
        query = "select  :funno  funno,\n" +
                "b.gender_div              gender,\n" +
                "b.item_div                item,\n" +
                "nvl(b.yedaham,0)          yedaham,\n" +
                "nvl(b.outsourcing,0)      outsourcing,\n" +
                "nvl(b.sum_div,0)          total,\n" +
                "0 AS remainCount \n" +
                "from dual a\n" +
                "left outer join(\n" +
                "select '남' gender_div,\n" +
                "    bb.*\n" +
                "from dual aa\n" +
                "left outer join (\n" +
                "    select '상하의세트' item_div,\n" +
                "           b.*\n" +
                "      from dual a\n" +
                "      left outer join (\n" +
                "             select a.fun_ctrl_no,\n" +
                "                   sum(CASE WHEN c.type = '1' and c.division = '2' THEN a.amount else 0 END) yedaham,\n" +
                "                   sum(CASE WHEN c.type = '1' and c.division = '1' THEN a.amount else 0 END) outsourcing,\n" +
                "                   sum(CASE WHEN c.type = '1' THEN a.amount else 0 END) sum_div\n" +
                "              from ch013d a\n" +
                "              left outer join sb012m b on a.ITEM = b.ITEM\n" +
                "              left outer join sb012m_div c on a.item = c.item\n" +
                "             where a.CK_NO_TYPE = '1'\n" +
                "               and a.fun_ctrl_no = :funno\n" +
                "             group by a.fun_ctrl_no\n" +
                "      ) b on 1=1\n" +
                "     union all\n" +
                "    select '상의' item_div,\n" +
                "           b.*\n" +
                "      from dual a\n" +
                "      left outer join (\n" +
                "             select a.fun_ctrl_no,\n" +
                "                   sum(CASE WHEN c.type = '2' and c.division = '2' THEN a.amount else 0 END) yedaham,\n" +
                "                   sum(CASE WHEN c.type = '2' and c.division = '1' THEN a.amount else 0 END) outsourcing,\n" +
                "                   sum(CASE WHEN c.type = '2' THEN a.amount else 0 END) sum_div\n" +
                "              from ch013d a\n" +
                "              left outer join sb012m b on a.ITEM = b.ITEM\n" +
                "              left outer join sb012m_div c on a.item = c.item\n" +
                "             where a.CK_NO_TYPE = '1'\n" +
                "               and a.fun_ctrl_no = :funno\n" +
                "             group by a.fun_ctrl_no\n" +
                "      ) b on 1=1\n" +
                "     union all\n" +
                "    select '하의' item_div,\n" +
                "           b.*\n" +
                "      from dual a\n" +
                "      left outer join (\n" +
                "             select a.fun_ctrl_no,\n" +
                "                   sum(CASE WHEN c.type = '3' and c.division = '2' THEN a.amount else 0 END) yedaham,\n" +
                "                   sum(CASE WHEN c.type = '3' and c.division = '1' THEN a.amount else 0 END) outsourcing,\n" +
                "                   sum(CASE WHEN c.type = '3' THEN a.amount else 0 END) sum_div\n" +
                "              from ch013d a\n" +
                "              left outer join sb012m b on a.ITEM = b.ITEM\n" +
                "              left outer join sb012m_div c on a.item = c.item\n" +
                "             where a.CK_NO_TYPE = '1'\n" +
                "               and a.fun_ctrl_no = :funno\n" +
                "             group by a.fun_ctrl_no\n" +
                "      ) b on 1=1\n" +
                ") bb on 1=1\n" +
                "union all\n" +
                "select '여'gender_div,\n" +
                "   bb.*\n" +
                "from dual aa\n" +
                "left outer join (\n" +
                "   select '상하의세트' item_div,\n" +
                "           b.*\n" +
                "      from dual a\n" +
                "      left outer join (\n" +
                "             select a.fun_ctrl_no,\n" +
                "                   sum(CASE WHEN c.type = '4' and c.division = '2' THEN a.amount else 0 END) yedaham,\n" +
                "                   sum(CASE WHEN c.type = '4' and c.division = '1' THEN a.amount else 0 END) outsourcing,\n" +
                "                   sum(CASE WHEN c.type = '4' THEN a.amount else 0 END) sum_div\n" +
                "              from ch013d a\n" +
                "              left outer join sb012m b on a.ITEM = b.ITEM\n" +
                "              left outer join sb012m_div c on a.item = c.item\n" +
                "             where a.CK_NO_TYPE = '1'\n" +
                "               and a.fun_ctrl_no = :funno\n" +
                "             group by a.fun_ctrl_no\n" +
                "      ) b on 1=1\n" +
                "     union all\n" +
                "    select '상의' item_div,\n" +
                "            b.*\n" +
                "      from dual a\n" +
                "      left outer join (\n" +
                "             select a.fun_ctrl_no,\n" +
                "                   sum(CASE WHEN c.type = '5' and c.division = '2' THEN a.amount else 0 END) yedaham,\n" +
                "                   sum(CASE WHEN c.type = '5' and c.division = '1' THEN a.amount else 0 END) outsourcing,\n" +
                "                   sum(CASE WHEN c.type = '5' THEN a.amount else 0 END) sum_div\n" +
                "              from ch013d a\n" +
                "              left outer join sb012m b on a.ITEM = b.ITEM\n" +
                "              left outer join sb012m_div c on a.item = c.item\n" +
                "             where a.CK_NO_TYPE = '1'\n" +
                "               and a.fun_ctrl_no = :funno\n" +
                "             group by a.fun_ctrl_no\n" +
                "      ) b on 1=1\n" +
                "     union all\n" +
                "     select '하의' item_div,\n" +
                "            b.*\n" +
                "      from dual a\n" +
                "      left outer join (\n" +
                "             select a.fun_ctrl_no,\n" +
                "                   sum(CASE WHEN c.type = '6' and c.division = '2' THEN a.amount else 0 END) yedaham,\n" +
                "                   sum(CASE WHEN c.type = '6' and c.division = '1' THEN a.amount else 0 END) outsourcing,\n" +
                "                   sum(CASE WHEN c.type = '6' THEN a.amount else 0 END) sum_div\n" +
                "              from ch013d a\n" +
                "              left outer join sb012m b on a.ITEM = b.ITEM\n" +
                "              left outer join sb012m_div c on a.item = c.item\n" +
                "             where a.CK_NO_TYPE = '1'\n" +
                "               and a.fun_ctrl_no = :funno\n" +
                "             group by a.fun_ctrl_no\n" +
                "      ) b on 1=1\n" +
                ") bb on 1=1\n" +
                ") b on 1=1",
        resultClass = CarallocationRentalEntitiy.class,
        resultSetMapping = "RentalStatusListMapping")

@Data
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name="ch013d", schema = "tk_bodev")
public class CarallocationRentalEntitiy {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RENTAL_SEQ")
    @SequenceGenerator(sequenceName = "RENTAL_SEQ", allocationSize = 1, name = "RENTAL_SEQ")
    private long seq;
    private String line;
    private String funCtrlNo;
    private String itemCkNo;
    private String gubun;
    private String item;
    private long amount;
    private String rentalDate;
    private long highLine;
    private String dept;
    private String ckNoType;

    @Builder
    public CarallocationRentalEntitiy(CarallocationRental carallocationRental) {
        this.line = carallocationRental.getLine();
        this.funCtrlNo = carallocationRental.getFunCtrlNo();
        this.itemCkNo = carallocationRental.getItemCkNo();
        this.gubun = carallocationRental.getGubun();
        this.item = carallocationRental.getItem();
        this.amount = carallocationRental.getAmount();
        this.rentalDate = carallocationRental.getRentalDate();
        this.highLine = carallocationRental.getHighLine();
        this.dept = carallocationRental.getDept();
        this.ckNoType = "1";
    }
}
