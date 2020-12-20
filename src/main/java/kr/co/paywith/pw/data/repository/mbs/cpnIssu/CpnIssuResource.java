package kr.co.paywith.pw.data.repository.mbs.cpnIssu;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class CpnIssuResource extends Resource<CpnIssu> {

    public CpnIssuResource(CpnIssu cpnIssu, Link... links) {
        super(cpnIssu, links);
        add(linkTo(CpnIssuController.class).slash(cpnIssu.getId()).withSelfRel());

    }
}
