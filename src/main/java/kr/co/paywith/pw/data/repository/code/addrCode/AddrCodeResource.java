package kr.co.paywith.pw.data.repository.code.addrCode;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class AddrCodeResource extends Resource<AddrCode> {

    public AddrCodeResource(AddrCode addrCode, Link... links) {
        super(addrCode, links);
        add(linkTo(AddrCodeController.class).slash(addrCode.getCd()).withSelfRel());

    }
}
