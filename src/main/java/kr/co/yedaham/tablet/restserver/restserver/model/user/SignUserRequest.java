package kr.co.yedaham.tablet.restserver.restserver.model.user;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class SignUserRequest {
    private String id;
    private String password;
}
