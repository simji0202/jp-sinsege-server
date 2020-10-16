package kr.co.paywith.pw.data.repository.busCompany;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class BusCompanyResource extends Resource<BusCompany> {
	public BusCompanyResource(BusCompany busCompany, Link... links) {
		super(busCompany, links);
		add(linkTo(BusCompanyController.class).slash(busCompany.getId()).withSelfRel());

	}
}
