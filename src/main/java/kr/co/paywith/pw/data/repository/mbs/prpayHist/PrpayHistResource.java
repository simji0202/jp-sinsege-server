package kr.co.paywith.pw.data.repository.mbs.prpayHist;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class PrpayHistResource extends Resource<PrpayHist> {
	public PrpayHistResource(PrpayHist prpayHist, Link... links) {
		super(prpayHist, links);
		add(linkTo(PrpayHistController.class).slash(prpayHist.getId()).withSelfRel());

	}
}
