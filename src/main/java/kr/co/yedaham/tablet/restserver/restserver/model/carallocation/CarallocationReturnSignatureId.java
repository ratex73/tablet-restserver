package kr.co.yedaham.tablet.restserver.restserver.model.carallocation;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@RequiredArgsConstructor
public class CarallocationReturnSignatureId implements Serializable {
    @Column(name = "FUN_CTRL_NO")
    private String funno;
    @Column(name = "ADD_ITEM_CK_NO")
    private String addItemCkNo;
}
