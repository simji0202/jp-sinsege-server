package kr.co.paywith.pw.mapper;


import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.partners.PartnersListExcel;
import kr.co.paywith.pw.data.repository.requests.RequestsInfo;
import kr.co.paywith.pw.data.repository.requests.RequestsListExcel;
import kr.co.paywith.pw.data.repository.staistics.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "statisticsMapper")
public interface StatisticsMapper {



//  /**
//   * 상담 이력 통계 리스트
//   **/
//  List<RequestStatisticsDto> selectRequestStatics(@Param("searchForm") SearchForm searchForm);
//
//  /**
//   * 이사 업체
//   **/
//  SalesStatisticsDto selectPartners(@Param("id") Integer id);
//
//  /**
//   * 이사 업체 통계
//   **/
//  PartnersStatisticsDto selectPartnersStatics();

  FrontStatisticsDto selectFrontStatics();



//  List<PartnersListExcel> excelPartnersList(@Param("searchForm") SearchForm searchForm);
//
//  List<RequestsListExcel> excelRequestsList(@Param("searchForm") SearchForm searchForm);

//  List<RequestAlarmsList> alarmdateList(@Param("searchForm") SearchForm searchForm);

//  List<PartnerShipAlarmsList> partnerShipAlarmsList(@Param("searchForm") SearchForm searchForm);

//  List<Integer> partnerClosedates(@Param("wishMoveDate") String wishMoveDate);

 List<RequestsInfo> requestInfo(@Param("searchForm") SearchForm searchForm);
}
