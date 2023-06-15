package kr.co.yedaham.tablet.restserver.restserver.resp;

import kr.co.yedaham.tablet.restserver.restserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepo extends JpaRepository<User, Long> {

    Optional<User> findByUserid(String userid);
}
