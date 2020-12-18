package kr.co.paywith.pw.data.repository.mbs.cd;

import io.swagger.annotations.Api;

import kr.co.paywith.pw.data.repository.mbs.cd.addr.Addr;
import kr.co.paywith.pw.data.repository.mbs.cd.addr.AddrRepository;
import kr.co.paywith.pw.data.repository.mbs.cd.addrSub.AddrSub;
import kr.co.paywith.pw.data.repository.mbs.cd.addrSub.AddrSubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/brand/{brandCd}/cd")
@Api(value = "CdBrandController", description = "선불카드 코드 API", basePath = "/api/brand/{brandCd}/cd")
public class CdBrandController {

  @Autowired
  AddrRepository addrRepository;

  @Autowired
  AddrSubRepository addrSubRepository;

  @GetMapping("/addr")
  public List<Addr> getAddr() {
    return addrRepository.findAll();
  }

  @GetMapping("/addr-sub")
  public List<AddrSub> getAddrSub(AddrSub addrSub) {

    ExampleMatcher m = ExampleMatcher.matchingAll()
        .withMatcher("addrCd", match -> match.exact());

    Example<AddrSub> e = Example.of(addrSub, m);

    return addrSubRepository.findAll(e);
  }

}
