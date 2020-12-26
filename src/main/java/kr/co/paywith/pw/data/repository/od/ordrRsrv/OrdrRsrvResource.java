package kr.co.paywith.pw.data.repository.od.ordrRsrv;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class OrdrRsrvResource extends Resource<OrdrRsrv> {
	public OrdrRsrvResource(OrdrRsrv ordrRsrv, Link... links) {
		super(ordrRsrv, links);
		add(linkTo(OrdrRsrvController.class).slash(ordrRsrv.getId()).withSelfRel());

	}
}
