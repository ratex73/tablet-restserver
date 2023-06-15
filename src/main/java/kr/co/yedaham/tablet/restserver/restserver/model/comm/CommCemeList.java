package kr.co.yedaham.tablet.restserver.restserver.model.comm;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommCemeList {
    private long seq;
    private String funCd;
    private String funNm;
    private String category;
    private String lpost;
    private String spost;
    private String filePath;
    private String fileInfo;

}
