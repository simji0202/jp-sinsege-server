package kr.co.paywith.pw.data.repository.mbs.delng;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import kr.co.paywith.pw.data.repository.mbs.delng.Delng;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class DelngResource extends Resource<Delng> {

    public DelngResource(Delng delng, Link... links) {
        super(delng, links);
        add(linkTo(DelngController.class).slash(delng.getId()).withSelfRel());

    }
}
