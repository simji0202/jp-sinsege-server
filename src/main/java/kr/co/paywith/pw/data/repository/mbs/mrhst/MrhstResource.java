package kr.co.paywith.pw.data.repository.mbs.mrhst;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class MrhstResource extends Resource<Mrhst> {

    public MrhstResource(Mrhst mrhst, Link... links) {
        super(mrhst, links);
        add(linkTo(MrhstController.class).slash(mrhst.getId()).withSelfRel());

    }
}
