package kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl;


import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class MrhstTrmnlResource extends Resource<MrhstTrmnl> {

    public MrhstTrmnlResource(MrhstTrmnl mrhstTrmnl, Link... links) {
        super(mrhstTrmnl, links);
        add(linkTo(MrhstTrmnlController.class).slash(mrhstTrmnl.getId()).withSelfRel());

    }
}
