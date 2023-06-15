package kr.co.yedaham.tablet.restserver.restserver.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@DynamicUpdate
@Table(name="sb016m", schema = "tk_bodev")
public class CarallocationMiddleCategoryEntity {
    @Id
    @Column(name = "GROUPCD2")
    private String groupcd;
    @Column(name = "GROUPNM2")
    private String groupnm;
}
