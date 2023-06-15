package kr.co.yedaham.tablet.restserver.restserver.model.carallocation;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CarallocationReturnDto {
    List<CarallocationReturn> carallocationReturnList;
    CarallocationReturnSignature returnSignature;
}
