package kr.co.yedaham.tablet.restserver.restserver.model.funmessage;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class FunMessageDeleteRequest {
    private long seq;
    private String funCtrlNo;
}
