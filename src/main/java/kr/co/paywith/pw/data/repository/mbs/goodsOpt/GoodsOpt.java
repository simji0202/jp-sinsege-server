package kr.co.paywith.pw.data.repository.mbs.goodsOpt;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.goodsOptMaster.GoodsOptMaster;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class GoodsOpt {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("식별번호")
  private Integer id;

//
//
//	 상품      가격 5000
//	   -- 옵션 1   카데고리
//	   -- 옵션 2
//	   -- 옵션 3
//	   -- 옵션 4
//
//	 상품    가격 6000
//			-- 기 1   *
//			-- 드레싱  2
//	        -- 옵션 1   * 3
//          -- 옵션
//
//
//	패키지 상품
//	  상품 1
//	  상품 2

  private String optTitle;


  @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
  private List<GoodsOptMaster> goodsOptMasterList = new ArrayList<GoodsOptMaster>();

  /**
   * 다중 선택 여부 ( 라디오버튼 : false , 체크박스 : true )
   */
  private Boolean multiChoiceFl = false;

  /**
   * 필수 여부
   */
  private Boolean requiredFl = true;

  /**
   * 표시 순서
   */
  private Integer sort = 0;

  /**
   * 사용 여부
   */
  private Boolean activeFl = true;


  @CreationTimestamp
  private LocalDateTime regDttm;

  @UpdateTimestamp
  private LocalDateTime updtDttm;

  /**
   * 추가한 관리자 부하를 줄이기 위해 감소 시키지 위해 해당 아이디만 저장
   */
  private String createBy;

  /**
   * 변경한  관리자 부하를 줄이기 위해 감소 시키지 위해 해당 아이디만 저장
   */
  private String updateBy;

}