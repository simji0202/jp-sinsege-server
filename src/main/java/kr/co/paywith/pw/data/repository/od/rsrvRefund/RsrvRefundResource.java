package kr.co.paywith.pw.data.repository.od.rsrvRefund;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class RsrvRefundResource extends Resource<RsrvRefund> {
	public RsrvRefundResource(RsrvRefund rsrvRefund, Link... links) {
		super(rsrvRefund, links);
		add(linkTo(RsrvRefundController.class).slash(rsrvRefund.getId()).withSelfRel());

	}
}
