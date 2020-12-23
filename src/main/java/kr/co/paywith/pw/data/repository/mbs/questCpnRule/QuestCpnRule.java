package kr.co.paywith.pw.data.repository.mbs.questCpnRule;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * 퀘스트 쿠폰 규칙
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class QuestCpnRule {

    /**
     * 퀘스트 쿠폰 규칙 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer questCpnRuleSn;

    /**
     * 퀘스트 쿠폰 규칙 이름
     */
    private String questCpnRuleNm;

    /**
     * 최대 달성 횟수
     */
    private Integer maxCnt; // 최대 달성 횟수

//  /**
//   * 퀘스트 쿠폰 상품 세트
//   */
//  @OneToMany(mappedBy = "questCpnRule")
//  private Set<QuestCpnGoods> questCpnGoodsSet;

    /**
     * 등록 일시
     */
    @CreationTimestamp
    private ZonedDateTime regDttm;

    /**
     * 수정 일시
     */
    @UpdateTimestamp
    private ZonedDateTime updtDttm;
}
