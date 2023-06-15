package kr.co.yedaham.tablet.restserver.restserver.model.funmessage;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class FunMessageInfo {
      private long seq;
      private String funCtrlNo;
      private String regDate;
      private String regTime;
      private String updateDate;
      private String updateTime;
      private String mourNm;
      private String reat;
      private String certNo;
      private String deadNm;
      private String ceme;
      private String chuDuty;
      private String sex;
      private String deadRea;
      private String deadDate;
      private String caskDate;
      private String cortDate;
      private String memo;
      private String mourAccState;
      private String funPlace;
      private String funTelNo;
      private long state;
      private String funNm;
      private String ceme2;
      private String mourPhone;
      private long historyNo;
      private String flag;
      private LocalDateTime historyDate;
      private long deadAge;
      private String sendYn;
      private String replyYn;
      private long hitCount;


}
