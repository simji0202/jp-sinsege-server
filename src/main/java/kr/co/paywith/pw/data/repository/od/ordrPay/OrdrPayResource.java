package kr.co.paywith.pw.data.repository.od.ordrPay;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class OrdrPayResource extends Resource<OrdrPay> {
	public OrdrPayResource(OrdrPay ordrPay, Link... links) {
		super(ordrPay, links);
		add(linkTo(OrdrPayController.class).slash(ordrPay.getId()).withSelfRel());

	}
}
