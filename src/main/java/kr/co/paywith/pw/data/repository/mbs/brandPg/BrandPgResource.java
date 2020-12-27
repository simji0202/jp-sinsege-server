package kr.co.paywith.pw.data.repository.mbs.brandPg;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class BrandPgResource extends Resource<BrandPg> {
	public BrandPgResource(BrandPg brandPg, Link... links) {
		super(brandPg, links);
		add(linkTo(BrandPgController.class).slash(brandPg.getId()).withSelfRel());

	}
}
