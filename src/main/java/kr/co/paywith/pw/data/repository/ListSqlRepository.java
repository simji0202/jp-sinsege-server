package kr.co.paywith.pw.data.repository;

import com.querydsl.core.BooleanBuilder;

import kr.co.paywith.pw.data.repository.partners.PartnersListDto;
import kr.co.paywith.pw.data.repository.requests.RequestsList;
import kr.co.paywith.pw.data.repository.staistics.PointStatisticsDto;
import kr.co.paywith.pw.data.repository.staistics.RequestReviewsStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ListSqlRepository {

  List<PartnersListDto> findAllMessagePartnersList(BooleanBuilder booleanBuilder);

  Page<PartnersListDto> findAllPartnersList(BooleanBuilder booleanBuilder, Pageable pageable);



  Page<RequestsList> findAllRequestsList(BooleanBuilder booleanBuilder, Pageable pageable);

  RequestsList findOneRequest(Integer requestsId, Integer partnersId, Integer assignId);

  RequestsList findOneRequest(Integer requestsId, Integer partnersId);


}
