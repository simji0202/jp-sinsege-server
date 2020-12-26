package kr.co.paywith.pw.data.repository.mbs.file;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {

	@NameDescription("식별번호")
	private Integer id;

	/**
	 * 첨부파일 순서
	 */
	private Integer ordr;
	/**
	 * 첨부파일 원본 파일명
	 */
	private String originalNm;
	/**
	 * 첨부파일 웹 경로
	 */
	private String fileUrl;

	/**
	 * 게시판 일련번호
	 */
//    @Column(table = "PW_FILE_BBS")
	private Integer bbsSn;

}