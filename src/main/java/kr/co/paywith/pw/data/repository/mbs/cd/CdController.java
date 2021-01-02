package kr.co.paywith.pw.data.repository.mbs.cd;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import kr.co.paywith.pw.data.repository.mbs.abs.CommonController;
import kr.co.paywith.pw.data.repository.mbs.cd.addr.CdAddr1;
import kr.co.paywith.pw.data.repository.mbs.cd.addr.CdAddr1Repository;
import kr.co.paywith.pw.data.repository.mbs.cd.addr.CdAddr1ResponseDto;
import kr.co.paywith.pw.data.repository.mbs.cd.addr2.CdAddr2Repository;
import kr.co.paywith.pw.data.repository.mbs.cd.addr2.CdAddr2ResponseDto;
import kr.co.paywith.pw.data.repository.mbs.cd.addr2.QCdAddr2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/cd")
@Api(value = "CdController", description = "코드관리 API", basePath = "/api/cd")
@Slf4j
public class CdController extends CommonController {

  @Autowired
  CdAddr1Repository cdAddr1Repository;

  @Autowired
  CdAddr2Repository cdAddr2Repository;

  @GetMapping("/addr1")
  public Page<CdAddr1ResponseDto> getCdAddrList(
      CdAddr1 cdAddr1,
      Pageable pageable) {
    ExampleMatcher m = ExampleMatcher.matchingAll();
//				.withMatcher("brandSn", match -> match.exact())

    Example<CdAddr1> e = Example.of(cdAddr1, m);

    return cdAddr1Repository.findAll(e, pageable)
        .map(addr1 -> {
          return modelMapper.map(addr1, CdAddr1ResponseDto.class);
        });
  }

  @GetMapping("/addr2")
  public Page<CdAddr2ResponseDto> getCdAddr2List(
      @RequestParam(required = false) String addr1Cd,
      Pageable pageable) {
    BooleanBuilder bb = new BooleanBuilder();
    QCdAddr2 qCdAddr2 = QCdAddr2.cdAddr2;

    if (addr1Cd != null) {
      bb.and(qCdAddr2.cdAddr1.cd.eq(addr1Cd));
    }

    return cdAddr2Repository.findAll(bb, pageable)
        .map(addr2 -> modelMapper.map(addr2, CdAddr2ResponseDto.class));
  }
}
