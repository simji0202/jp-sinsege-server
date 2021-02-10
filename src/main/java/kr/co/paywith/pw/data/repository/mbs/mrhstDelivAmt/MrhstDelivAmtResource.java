package kr.co.paywith.pw.data.repository.mbs.mrhstDelivAmt;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class MrhstDelivAmtResource extends Resource<MrhstDelivAmt> {
	public MrhstDelivAmtResource(MrhstDelivAmt mrhstDelivAmt, Link... links) {
		super(mrhstDelivAmt, links);
		add(linkTo(MrhstDelivAmtController.class).slash(mrhstDelivAmt.getId()).withSelfRel());

	}
}
