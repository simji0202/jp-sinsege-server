package kr.co.paywith.pw.data.repository.mbs.cd;

import io.swagger.annotations.Api;

import kr.co.paywith.pw.data.repository.mbs.cd.addr.Addr;
import kr.co.paywith.pw.data.repository.mbs.cd.addr.AddrRepository;
import kr.co.paywith.pw.data.repository.mbs.cd.addrSub.AddrSub;
import kr.co.paywith.pw.data.repository.mbs.cd.addrSub.AddrSubRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/mngr/cd")
@Api(value = "CdMngrController", description = "관리자 코드관리 API", basePath = "/api/mngr/cd")
public class CdMngrController {

  private static final Logger logger = LoggerFactory.getLogger(CdMngrController.class);

  @Autowired
  AddrRepository addrRepository;

  @Autowired
  AddrSubRepository addrSubRepository;

  @GetMapping("/addr")
  public Page<Addr> getAddrList(Addr addr, Pageable pageable) {
    ExampleMatcher m = ExampleMatcher.matchingAll();
//				.withMatcher("brandSn", match -> match.exact())

    Example<Addr> e = Example.of(addr, m);

    return addrRepository.findAll(e, pageable);
  }

  @GetMapping("/addr-sub")
  public Page<AddrSub> getAddrSubList(AddrSub addrSub, Pageable pageable) {
    ExampleMatcher m = ExampleMatcher.matchingAll()
        .withMatcher("addrCd", match -> match.exact());

    Example<AddrSub> e = Example.of(addrSub, m);

    return addrSubRepository.findAll(e, pageable);
  }

}
