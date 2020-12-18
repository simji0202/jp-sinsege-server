package kr.co.paywith.pw.data.repository.mbs.cd;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;

import kr.co.paywith.pw.data.repository.mbs.abs.CommonController;
import kr.co.paywith.pw.data.repository.mbs.cd.addr.Addr;
import kr.co.paywith.pw.data.repository.mbs.cd.addr.AddrRepository;
import kr.co.paywith.pw.data.repository.mbs.cd.addr.AddrResponseDto;
import kr.co.paywith.pw.data.repository.mbs.cd.addrSub.AddrSubRepository;
import kr.co.paywith.pw.data.repository.mbs.cd.addrSub.AddrSubResponseDto;
import kr.co.paywith.pw.data.repository.mbs.cd.addrSub.QAddrSub;
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
  AddrRepository addrRepository;

  @Autowired
  AddrSubRepository addrSubRepository;

  @GetMapping("/addr")
  public Page<AddrResponseDto> getAddrList(
      Addr addr,
      Pageable pageable) {
    ExampleMatcher m = ExampleMatcher.matchingAll();
//				.withMatcher("brandSn", match -> match.exact())

    Example<Addr> e = Example.of(addr, m);

    return addrRepository.findAll(e, pageable)
        .map(addr2 -> {
          AddrResponseDto dto = modelMapper.map(addr2, AddrResponseDto.class);
          dto.setName(addr2.getAddrName());
          return dto;
        });
  }

  @GetMapping("/addr-sub")
  public Page<AddrSubResponseDto> getAddrSubList(
      @RequestParam(required = false) String addrCd,
      Pageable pageable) {
    BooleanBuilder bb = new BooleanBuilder();
    QAddrSub qAddrSub = QAddrSub.addrSub;
    if (addrCd != null) {
      bb.and(qAddrSub.addrCd.eq(addrCd));
    }

    return addrSubRepository.findAll(bb, pageable)
        .map(addrSub -> {
          AddrSubResponseDto dto = modelMapper.map(addrSub, AddrSubResponseDto.class);
          dto.setName(addrSub.getAddrSubName());
          return dto;
        });
  }
}
