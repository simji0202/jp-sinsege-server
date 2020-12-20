package kr.co.paywith.pw.data.repository.mbs.bbs;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class BbsResource extends Resource<Bbs> {

    public BbsResource(Bbs bbs, Link... links) {
        super(bbs, links);
        add(linkTo(BbsController.class).slash(bbs.getId()).withSelfRel());

    }
}
