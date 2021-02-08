package kr.co.paywith.pw.data.repository.mbs.delngReview;

import javax.persistence.Id;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.ZonedDateTime;
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class DelngReview { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("일련번호")
	private Integer id;

	@NameDescription("제목")
	private String  sj;

	@NameDescription("내용")
	private String  cn;

	@NameDescription("점수")
	private int  score;

	@NameDescription("매장_주문_일련번호")
	private int  mrhstOrdrId;

	@NameDescription("거래_주문_일련번호")
	private int  delngOrdrId;

}
