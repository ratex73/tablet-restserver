package kr.co.yedaham.tablet.restserver.restserver.resp.contractamt;


import kr.co.yedaham.tablet.restserver.restserver.entity.ContractAmtEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.contractamt.Contract;
import kr.co.yedaham.tablet.restserver.restserver.model.contractamt.ContractAmt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface ContractAmtResp extends JpaRepository<ContractAmtEntity, String> {
    @Query(name = "findContractAmt", nativeQuery = true)
    ContractAmt findContractAmt(@Param("certno") String certno, @Param("functrlno") String functrlno);

    @Query(name = "findContract", nativeQuery = true)
    Contract findContract(@Param("certno") String certno);
}
