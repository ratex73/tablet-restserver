package kr.co.yedaham.tablet.restserver.restserver.entity;


import kr.co.yedaham.tablet.restserver.restserver.model.comm.CommCemeList;
import kr.co.yedaham.tablet.restserver.restserver.model.funmessage.FunMessageInfo;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;







@SqlResultSetMapping(
        name = "getCommCemeListMapping",
        classes = @ConstructorResult(
                targetClass = CommCemeList.class,
                columns = {
                        @ColumnResult(name = "SEQ", type = long.class),
                        @ColumnResult(name = "FUN_CD", type = String.class),
                        @ColumnResult(name = "FUN_NM", type = String.class),
                        @ColumnResult(name = "CATEGORY", type = String.class),
                        @ColumnResult(name = "LPOST", type = String.class),
                        @ColumnResult(name = "SPOST", type = String.class),
                        @ColumnResult(name = "FILE_PATH", type = String.class),
                        @ColumnResult(name = "FILE_INFO", type = String.class),

                })
)

@NamedNativeQuery(name = "getCommCemeList",
        query = "SELECT SEQ,\n" +
                "       FUN_CD,\n" +
                "       FUN_NM,\n" +
                "       CATEGORY,\n" +
                "       LPOST,\n" +
                "       SPOST,\n" +
                "       FILE_PATH,\n" +
                "       FILE_INFO\n" +
                "FROM FUN_CEME\n" +
                "WHERE FUN_CD  =:funCd\n" +
                "ORDER BY SEQ",
        resultClass = CommCemeEntity.class,
        resultSetMapping = "getCommCemeListMapping")






@Data
@Entity
@NoArgsConstructor
@Getter
@ToString
@Table(name="FUN_CEME", schema = "tk_fsdev")
public class CommCemeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FUN_CEME_SEQ")
    @SequenceGenerator(sequenceName = "FUN_CEME_SEQ", allocationSize = 1, name = "FUN_CEME_SEQ")
    @Column(name = "SEQ")
    private long seq;
    @Column(name = "FUN_CD")
    private String funCd;
    @Column(name = "FUN_NM")
    private String funNm;
    @Column(name = "CATEGORY")
    private String category;
    @Column(name = "LPOST")
    private String lpost;
    @Column(name = "SPOST")
    private String spost;
    @Column(name = "FILE_PATH")
    private String filePath;
    @Column(name = "FILE_INFO")
    private String fileInfo;
}
