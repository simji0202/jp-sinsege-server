package kr.co.paywith.pw.data.repository.mbs.refund;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class RefundResource extends Resource<Refund> {
	public RefundResource(Refund refund, Link... links) {
		super(refund, links);
		add(linkTo(RefundController.class).slash(refund.getId()).withSelfRel());

	}
}
