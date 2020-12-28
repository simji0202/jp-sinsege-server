package kr.co.paywith.pw.data.repository.od.cate;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class CateResource extends Resource<Cate> {
	public CateResource(Cate cate, Link... links) {
		super(cate, links);
		add(linkTo(CateController.class).slash(cate.getId()).withSelfRel());

	}
}
