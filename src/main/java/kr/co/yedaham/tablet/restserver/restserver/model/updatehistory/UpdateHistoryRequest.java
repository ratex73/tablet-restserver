package kr.co.yedaham.tablet.restserver.restserver.model.updatehistory;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateHistoryRequest {
    private String updateid;
    private String updatedate;
    private String frsrchdate;
    private String tosrchdate;
    private String functrlno;
}
