package kr.co.yedaham.tablet.restserver.restserver.model.comm;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CoComSrch2Request {
    private String funnm;
    private String addr;
    private String telno;
}
