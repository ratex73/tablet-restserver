package kr.co.yedaham.tablet.restserver.restserver.model.customer;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CustomerPaymentDto {
    private List<CustomerPaymentInfo> dataList;
}
