package kr.co.yedaham.tablet.restserver.restserver.resp.carallocation;

import kr.co.yedaham.tablet.restserver.restserver.entity.CarallocationInfoEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.CarallocationId;
import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.CarallocationMiddleProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarallocationInfoResp extends JpaRepository<CarallocationInfoEntity, CarallocationId> {
    @Query(value = "select nvl(sum(diary),0) from ch020d\n" +
            "where effdate = (select substr(fun_start_date,0,4)||'-'||substr(fun_start_date,5,2) from tbfu1001 \n" +
            "where fun_ctrl_no = :funno group by fun_start_date)", nativeQuery = true)
    public int getFinanceCount(@Param("funno") String funno);

    @Query(value = "SELECT nvl(max(line)+1,1) FROM CH012D WHERE FUN_CTRL_NO =:funno", nativeQuery = true)
    public int getNextValMySequence(@Param("funno") String funno);

    @Query(value = "select case when count(1) = 0 then 0\n" +
            "            when min(signature) is not null then 1\n" +
            "            else 2 end\n" +
            "from ch012d a\n" +
            "inner join (\n" +
            "                select fun_ctrl_no,max(line)line\n" +
            "                from ch012d\n" +
            "                where fun_ctrl_no = :funno\n" +
            "                group by fun_ctrl_no\n" +
            ")b on a.fun_ctrl_no = b.fun_ctrl_no and a.line = b.line", nativeQuery = true)
    public int getValueCarRegister(@Param("funno") String funno);

    public CarallocationInfoEntity findByCarallocationIdFunCtrlNoAndCarallocationIdLine(String funno, int line);

    @Query(value = "select a.item,a.desc1 itemnm\n" +
            "from sb012m a\n" +
            "inner join sb017m b on a.divcd = b.groupcd3\n" +
            "inner join sb016m c on b.groupcd1_disp = c.groupcd1_disp and b.groupcd2_disp = c.groupcd2_disp\n" +
            "where a.use_yn = 'Y'\n" +
            "and   c.groupcd2 = :groupcd\n" +
            "order by a.item", nativeQuery = true)
    public List<CarallocationMiddleProduct> getMiddleProductList(@Param("groupcd") String groupcd);


    @Query(value = "select max(line) from ch012d where fun_ctrl_no = :funno\n", nativeQuery = true)
    public int getMaxCarallocationLineCount(@Param("funno") String funno);
}