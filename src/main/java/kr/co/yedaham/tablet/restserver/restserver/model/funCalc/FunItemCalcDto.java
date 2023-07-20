package kr.co.yedaham.tablet.restserver.restserver.model.funCalc;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
//TBFU1004, TBFU1006, TBFU4447
public class FunItemCalcDto {
    List<FunItemInfo> funItemList;
    FunCalcInfo funCalcInfo;
    FunCareItemInfo funCareItemInfo;
    String prodgb;
    String certno;
}
