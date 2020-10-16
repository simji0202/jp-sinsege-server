package kr.co.paywith.pw.data.repository.partners;


import kr.co.paywith.pw.common.NameDescription;

public class PartnersListExcel {

  @NameDescription("NO")
  private String id;

  @NameDescription("지역")
  private String address;

  @NameDescription("아이디")
  private String adminId;

  @NameDescription("이사종류")
  private String partnersMoveService;

  @NameDescription("가입일자")
  private String createDate;

  @NameDescription("담당자")
  private String manager;

  @NameDescription("업체명")
  private String name;

  @NameDescription("연락처")
  private String phone;

  @NameDescription("추천 Y 갯수 ")
  private String recommendCount;

  @NameDescription("추천 N 갯수")
  private String recommendNoCount;

  @NameDescription("포인트잔액")
  private String point;

  @NameDescription("최종포인트 차감일")
  private String pointUpdateDate;

  private String status;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getAdminId() {
    return adminId;
  }

  public void setAdminId(String adminId) {
    this.adminId = adminId;
  }

  public String getPartnersMoveService() {
    return partnersMoveService;
  }

  public void setPartnersMoveService(String partnersMoveService) {
    this.partnersMoveService = partnersMoveService;
  }

  public String getCreateDate() {
    return createDate;
  }

  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }

  public String getManager() {
    return manager;
  }

  public void setManager(String manager) {
    this.manager = manager;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getRecommendCount() {
    return recommendCount;
  }

  public void setRecommendCount(String recommendCount) {
    this.recommendCount = recommendCount;
  }

  public String getRecommendNoCount() {
    return recommendNoCount;
  }

  public void setRecommendNoCount(String recommendNoCount) {
    this.recommendNoCount = recommendNoCount;
  }

  public String getPoint() {
    return point;
  }

  public void setPoint(String point) {
    this.point = point;
  }

  public String getPointUpdateDate() {
    return pointUpdateDate;
  }

  public void setPointUpdateDate(String pointUpdateDate) {
    this.pointUpdateDate = pointUpdateDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
