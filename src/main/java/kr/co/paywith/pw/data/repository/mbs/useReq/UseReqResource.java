package kr.co.paywith.pw.data.repository.mbs.useReq;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class UseReqResource extends Resource<UseReq> {

    public UseReqResource(UseReq useReq, Link... links) {
        super(useReq, links);
        add(linkTo(UseReqController.class).slash(useReq.getId()).withSelfRel());

    }
}
