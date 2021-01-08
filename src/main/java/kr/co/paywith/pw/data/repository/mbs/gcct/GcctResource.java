package kr.co.paywith.pw.data.repository.mbs.gcct;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import kr.co.paywith.pw.data.repository.mbs.gcct.Gcct;
import kr.co.paywith.pw.data.repository.mbs.gcct.GcctController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class GcctResource extends Resource<Gcct> {

    public GcctResource(Gcct gcct, Link... links) {
        super(gcct, links);
        add(linkTo(GcctController.class).slash(gcct.getId()).withSelfRel());

    }
}
