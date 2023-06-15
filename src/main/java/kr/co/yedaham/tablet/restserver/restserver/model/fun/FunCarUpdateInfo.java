package kr.co.yedaham.tablet.restserver.restserver.model.fun;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FunCarUpdateInfo {
    private String funno;
    private String limUseYn;
    private String busUseYn;
}
