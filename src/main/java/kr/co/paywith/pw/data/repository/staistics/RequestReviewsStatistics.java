package kr.co.paywith.pw.data.repository.staistics;


import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.partners.PartnerStatus;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class RequestReviewsStatistics {

  @NameDescription("ID ")
  private Integer id;

  @NameDescription("이사업체명 ")
  private String name;

  @NameDescription("상태")
  @Enumerated(EnumType.STRING)
  private PartnerStatus status;


  @NameDescription("전문성만족")
  private int proSatisfaction;

  @NameDescription("서비스만족")
  private int serviceSatisfaction;

  @NameDescription("신뢰도만족")
  private int reliabilitySatisfaction;

  @NameDescription("품질불만")
  private int qualityComplaint;

  @NameDescription("가격불만")
  private int priceComplaint;

  @NameDescription("친절도불만")
  private int kindlessComplaint;

  @NameDescription("신뢰도불만")
  private int reliabilityComplaint;

  @NameDescription("서비스불만")
  private int serviceComplaint;

}
