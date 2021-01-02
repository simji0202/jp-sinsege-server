package kr.co.paywith.pw.data.repository.od.cate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CateDto {

	@NameDescription("식별번호")
	private Integer id;

	private String cateNm;

	private String cateCd;

	private Integer sort;

	@ManyToOne
	private Brand brand;

	private Long parentId;

	private Boolean useFl;
}