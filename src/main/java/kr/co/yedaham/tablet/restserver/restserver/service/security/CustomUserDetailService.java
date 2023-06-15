package kr.co.yedaham.tablet.restserver.restserver.service.security;

import kr.co.yedaham.tablet.restserver.restserver.advice.exception.CUserNotFoundException;
import kr.co.yedaham.tablet.restserver.restserver.resp.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserJpaRepo userJpaRepo;

    public UserDetails loadUserByUsername(String userid) {
        return userJpaRepo.findByUserid(userid).orElseThrow(CUserNotFoundException::new);
    }
}
