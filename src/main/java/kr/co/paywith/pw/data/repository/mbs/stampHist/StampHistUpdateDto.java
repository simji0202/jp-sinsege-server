package kr.co.paywith.pw.data.repository.mbs.stampHist;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.opencsv.bean.CsvBindByName;
import kr.co.paywith.pw.data.repository.enumeration.StampHistTypeCd;
import kr.co.paywith.pw.data.repository.mbs.cpnIssu.CpnIssu;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.mbs.use.Use;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class StampHistUpdateDto {
	/**
	 * 회원
	 */
	private UserInfo userInfo;

	/**
	 * 스탬프 이력 구분 코
	 */
	@Enumerated(EnumType.STRING)
	private StampHistTypeCd stampHistTypeCd;

	/**
	 * 처리 일시
	 */
	private ZonedDateTime setleDttm;
	/**
	 * 스탬프 개수
	 */
	private Integer cnt = 0;
	/**
	 * 요청 단말기 번호
	 */
	private String trmnlNo;
	/**
	 * 단말기 영수증 번호
	 */
	private String trmnlDelngNo;

	/**
	 * 취소 일시
	 */
	private ZonedDateTime cancelRegDttm;

	/**
	 * 가맹점
	 */
	private Mrhst mrhst;

	/**
	 * 사용 이력
	 * <p>
	 * 스탬프 적립 시 / 스탬프 직접 사용 시 연결(이 둘은 useTypeCd로 구분한다)
	 */
	private Use use;

	/**
	 * 쿠폰 발급
	 */
	private CpnIssu cpnIssu;

}