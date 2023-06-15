package kr.co.yedaham.tablet.restserver.restserver.resp.fun;

import kr.co.yedaham.tablet.restserver.restserver.entity.FunSigninEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.file.FileSignin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FunSigininResp extends JpaRepository<FunSigninEntity, FileSignin> {
}
