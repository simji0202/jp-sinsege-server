package kr.co.paywith.pw.data.repository.mbs.delngOrdr;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class DelngOrdrResource extends Resource<DelngOrdr> {
	public DelngOrdrResource(DelngOrdr delngOrdr, Link... links) {
		super(delngOrdr, links);
		add(linkTo(DelngOrdrController.class).slash(delngOrdr.getId()).withSelfRel());

	}
}
