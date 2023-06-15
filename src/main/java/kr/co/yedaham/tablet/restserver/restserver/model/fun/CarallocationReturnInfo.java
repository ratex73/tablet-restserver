package kr.co.yedaham.tablet.restserver.restserver.model.fun;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CarallocationReturnInfo {
    private String item;
    private String itemNm;
    private String amountQty;
    private String returnAmount;
    private String noReturnAmount;
}
