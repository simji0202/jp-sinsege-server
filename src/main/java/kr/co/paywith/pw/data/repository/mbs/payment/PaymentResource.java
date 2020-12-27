package kr.co.paywith.pw.data.repository.mbs.payment;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class PaymentResource extends Resource<Payment> {
	public PaymentResource(Payment payment, Link... links) {
		super(payment, links);
		add(linkTo(PaymentController.class).slash(payment.getId()).withSelfRel());

	}
}
