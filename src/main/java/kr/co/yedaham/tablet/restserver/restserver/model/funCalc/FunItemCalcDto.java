package kr.co.yedaham.tablet.restserver.restserver.model.funCalc;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FunItemCalcDto {
    List<FunItemInfo> funItemList;
    FunCalcInfo funCalcInfo;
    FunCareItemInfo funCareItemInfo;
}
