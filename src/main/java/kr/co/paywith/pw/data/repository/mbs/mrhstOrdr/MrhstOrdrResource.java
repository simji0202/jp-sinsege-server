package kr.co.paywith.pw.data.repository.mbs.mrhstOrdr;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class MrhstOrdrResource extends Resource<MrhstOrdr> {
	public MrhstOrdrResource(MrhstOrdr mrhstOrdr, Link... links) {
		super(mrhstOrdr, links);
		add(linkTo(MrhstOrdrController.class).slash(mrhstOrdr.getId()).withSelfRel());

	}
}
