package kr.co.paywith.pw.data.repository.mbs.use;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class UseResource extends Resource<Use> {

    public UseResource(Use use, Link... links) {
        super(use, links);
        add(linkTo(UseController.class).slash(use.getId()).withSelfRel());

    }
}
