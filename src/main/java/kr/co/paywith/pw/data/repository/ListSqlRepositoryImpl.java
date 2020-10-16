package kr.co.paywith.pw.data.repository;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sun.istack.NotNull;
import kr.co.paywith.pw.data.repository.partners.PartnersListDto;
import kr.co.paywith.pw.data.repository.partners.QPartners;
import kr.co.paywith.pw.data.repository.requests.QRequests;
import kr.co.paywith.pw.data.repository.requests.RequestsList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static  kr.co.paywith.pw.data.repository.partners.QPartners.partners;
import static  kr.co.paywith.pw.data.repository.partners.QCompany.company;
import static  kr.co.paywith.pw.data.repository.requests.QRequests.requests;


@Transactional
@RequiredArgsConstructor
public class ListSqlRepositoryImpl implements ListSqlRepository {

  @Autowired
  EntityManager entityManager;

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<PartnersListDto> findAllMessagePartnersList(BooleanBuilder booleanBuilder) {


    QueryResults<PartnersListDto> results = jpaQueryFactory.select(Projections.bean(
        PartnersListDto.class,
        partners.id,
        partners.adminId,
        company.address.as("address"),
        partners.createDate,
        company.manager.as("manager"),
        partners.name,
        partners.phone,
        partners.status
    ))
        .from(partners)
        .where(booleanBuilder)
        .leftJoin(partners.company, company)
        .orderBy(partners.id.desc())
        .fetchResults();

    return results.getResults();
  }

  @Override
  public Page<PartnersListDto> findAllPartnersList(BooleanBuilder booleanBuilder,
                                                   Pageable pageable) {

    QPartners partners = QPartners.partners;

    QueryResults<PartnersListDto> results = jpaQueryFactory.select(Projections.bean(
        PartnersListDto.class,
        partners.id,
        company.companyNm.as("companyNm"),
        company.code.as("code"),
        partners.adminId,
        partners.name,
        partners.email,
        partners.phone,
        partners.createDate,
        partners.lastLoginDate,
        partners.status
    ))
        .from(partners)
        .where(booleanBuilder)
         .leftJoin(partners.company, company)
        .limit(pageable.getPageSize())
        .offset(pageable.getOffset())
        .orderBy(partners.id.desc())
        .fetchResults();

    return new PageImpl<>(results.getResults(), pageable, results.getTotal());
  }

  @Override
  public Page<RequestsList> findAllRequestsList(BooleanBuilder booleanBuilder, Pageable pageable) {


    QueryResults<RequestsList> results = jpaQueryFactory.select(Projections.bean(
        RequestsList.class,
        requests.id,
        company.companyNm.as("partnersName") ,
        requests.reqNo,
        requests.reqStateCd,
        requests.totBus,                // 요총버스수
        requests.seaterBus49,         // 49인승 지정버스
        requests.totBusDecide,
        requests.seaterBusDecide,
        requests.createDate,
        requests.departDate,       // 출발일자
        requests.departAreaCd,     // 출발지역
        requests.cruiseShipName,
        requests.travalAgencyName,
        requests.comment
    ))
        .from(requests)
        .where(booleanBuilder)
        .leftJoin(partners).on(requests.partners.id.eq(partners.id))
        .leftJoin(partners.company, company)
        .limit(pageable.getPageSize())
        .offset(pageable.getOffset())
        .orderBy(requests.id.desc())
        .fetchResults();

    return new PageImpl<>(results.getResults(), pageable, results.getTotal());
  }


  @Override
  public RequestsList findOneRequest(Integer requestsId, Integer partnersId, Integer assignId) {



    RequestsList results = jpaQueryFactory.select(Projections.bean(
        RequestsList.class,
        requests.id,
        requests.clientDevice,
        requests.createDate,
        requests.comment
    ))
        .from(requests)
        .where(requests.id.eq(requestsId))
        .fetchOne();


    return results;
  }

  @Override
  public RequestsList findOneRequest(Integer requestsId, Integer partnersId) {

    QPartners partners = QPartners.partners;
    QRequests requests = QRequests.requests;

    RequestsList results = jpaQueryFactory.select(Projections.bean(
        RequestsList.class,
        requests.id,
        requests.clientDevice,
        requests.createDate,
        requests.comment
    ))
        .from(requests)
        .where(requests.id.eq(requestsId))
        .fetchOne();

    return results;
  }

  private OrderSpecifier[] getOrderSpecifiers(@NotNull Pageable pageable, @NotNull Class klass) {

    String className = klass.getSimpleName();
    final String orderVariable = String.valueOf(Character.toLowerCase(className.charAt(0)))
        .concat(className.substring(1));

    return pageable.getSort().stream()
        .map(order -> new OrderSpecifier(
            Order.valueOf(order.getDirection().toString()),
            new PathBuilder(klass, orderVariable).get(order.getProperty()))
        )
        .toArray(OrderSpecifier[]::new);
  }
}
