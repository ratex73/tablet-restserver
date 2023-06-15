package kr.co.yedaham.tablet.restserver.restserver.resp.carallocation;

import kr.co.yedaham.tablet.restserver.restserver.entity.CarallocationRentalEntitiy;
import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.CarallocationRentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface CarallocationRentalResp extends JpaRepository<CarallocationRentalEntitiy, Long> {
    @Query(value = "select nvl(max(line),0)+1 from ch013d where fun_ctrl_no = :funno and item_ck_no = :itemno", nativeQuery = true)
    public String getNextLine(@Param("funno") String funno, @Param("itemno") String itemno);
    @Query(value = "SELECT MANAG_BRANCH FROM TBFU1001 WHERE FUN_CTRL_NO =:funno", nativeQuery = true)
    public String getDept(@Param("funno") String funno);
    @Query(name = "findRentalStatusListMapping", nativeQuery = true)
    List<CarallocationRentalStatus> findRentalStatusListMapping(@Param("funno") String funno);
    @Query(value =
            " select \n" +
            " NVL(sum(case when a.amount > 0  then a.amount else 0 end),0) count\n" +
            " from ch013d a\n" +
            " left outer join sb012m b on a.ITEM = b.ITEM\n" +
            " right outer join sb012m_div c on a.item = c.item\n" +
            " where a.CK_NO_TYPE = '1' \n" +
            " and a.fun_ctrl_no = :funno \n" +
            " and c.division = :division \n" +
            " and c.type = :type \n" +
            " order by c.type \n" , nativeQuery = true)
    public int getRentalCount(@Param("funno") String funno, @Param("division") String division, @Param("type") String type);


     @Query(value =
            " select \n" +
            " nvl(SUM(AMOUNT),0) totalRentalCont\n" +
            " from ch013d \n" +
            " where ITEM in  (select ITEM from sb012m_div)\n" +
            " and fun_ctrl_no = :funno \n" +
            " and CK_NO_TYPE = '1' \n", nativeQuery = true)
    public int getCompareRentalCount(@Param("funno") String funno);
}
