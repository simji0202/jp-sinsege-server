package kr.co.paywith.pw.data.repository.mbs.prpayIssu;

import java.util.ArrayList;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import kr.co.paywith.pw.data.repository.mbs.prpay.Prpay;
import kr.co.paywith.pw.data.repository.mbs.prpayGoods.PrpayGoods;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.ZonedDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class PrpayIssu {

  /**
   * 선불카드 발급 이력 일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("식별번호")
  private Integer id;


  /**
   * 선불카드 발급 이름
   */
  private String prpayIssuNm;

  /**
   * 선불카드 발급 장수
   */
  private Integer cnt;

  /**
   * 발급할 prpay에 일괄로 정할 유효일시
   */
  private ZonedDateTime validDttm;

  /**
   * 선불카드 종류
   */
  @ManyToOne
  private PrpayGoods prpayGoods;

  /**
   * 발급 선불카드 목록. create 시 prpay.prpayNo 를 넣으면 해당 번호로 카드를 생성한다
   */
  @OneToMany(mappedBy = "prpayIssu", cascade = {CascadeType.ALL})
  private List<Prpay> prpayList = new ArrayList<>();

  /**
   * 등록 일시
   */
  @CreationTimestamp
  private ZonedDateTime regDttm;

}