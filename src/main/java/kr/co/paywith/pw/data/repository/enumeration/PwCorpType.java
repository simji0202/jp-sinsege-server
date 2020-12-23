package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum PwCorpType {
  불명,
  비씨, 국민, 외환, 삼성, 신한,
  현대, 롯데, 씨티, 농협, 수협,
  우리, 하나, 주택, 광주, 전북,
  제주, 산업,
  한미, 신세계한미, 평화, 동남, 조흥, 강원, 축협,
  해외비자, 해외마스터, 해외다이너스, 해외JCB, 해외AMX, 동양해외, 은련;

  PwCorpType() {

  }
}
