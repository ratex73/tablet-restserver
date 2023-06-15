package kr.co.yedaham.tablet.restserver.restserver.model.updatehistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UpdateHistoryList {
    private long seq;
    private String updateInfo;
    private String functrlno;
    private String regDate;
    private String regId;

}
