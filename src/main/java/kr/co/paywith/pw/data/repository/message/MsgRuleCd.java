package kr.co.paywith.pw.data.repository.message;

public enum MsgRuleCd {

  // 구분_(상태_)메시지대상
  접수대기_고객,
  견적신청완료_업체,
  견적신청완료_고객,
  견적완료_고객,
  견적완료_마감_고객,
  견적완료_신청없음_고객,
  업체선정완료_업체,
  서비스이용완료_고객,

  // 2020.3.10 방문견적요청의 의해서 추가
  방문견적요청_고객,
  방문견적요청_업체,
  견적완료_방문_고객,
  업체선정완료_고객


}


