package kr.co.paywith.pw.data.repository.mbs.delngPayment;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class DelngPaymentResource extends Resource<DelngPayment> {
	public DelngPaymentResource(DelngPayment delngPayment, Link... links) {
		super(delngPayment, links);
		add(linkTo(DelngPaymentController.class).slash(delngPayment.getId()).withSelfRel());

	}
}
