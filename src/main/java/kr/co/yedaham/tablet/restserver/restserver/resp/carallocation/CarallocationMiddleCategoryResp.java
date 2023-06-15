package kr.co.yedaham.tablet.restserver.restserver.resp.carallocation;

import kr.co.yedaham.tablet.restserver.restserver.entity.CarallocationMiddleCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarallocationMiddleCategoryResp extends JpaRepository<CarallocationMiddleCategoryEntity, String> {
    public List<CarallocationMiddleCategoryEntity> findAllByOrderByGroupcdAsc();
}
