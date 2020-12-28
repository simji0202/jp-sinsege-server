package kr.co.paywith.pw.data.repository.od.optEtc;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class OptEtcResource extends Resource<OptEtc> {
	public OptEtcResource(OptEtc optEtc, Link... links) {
		super(optEtc, links);
		add(linkTo(OptEtcController.class).slash(optEtc.getId()).withSelfRel());

	}
}
