package kr.co.paywith.pw.data.repository.mbs.cpn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class CpnResource extends Resource<Cpn> {

    public CpnResource(Cpn cpn, Link... links) {
        super(cpn, links);
        add(linkTo(CpnController.class).slash(cpn.getId()).withSelfRel());

    }
}
