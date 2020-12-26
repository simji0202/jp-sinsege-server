package kr.co.paywith.pw.data.repository.mbs.chrgMass;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class ChrgMassResource extends Resource<ChrgMass> {
	public ChrgMassResource(ChrgMass chrgMass, Link... links) {
		super(chrgMass, links);
		add(linkTo(ChrgMassController.class).slash(chrgMass.getId()).withSelfRel());

	}
}
