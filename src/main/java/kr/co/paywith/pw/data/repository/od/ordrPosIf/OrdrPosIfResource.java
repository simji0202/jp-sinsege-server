package kr.co.paywith.pw.data.repository.od.ordrPosIf;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class OrdrPosIfResource extends Resource<OrdrPosIf> {
	public OrdrPosIfResource(OrdrPosIf ordrPosIf, Link... links) {
		super(ordrPosIf, links);
		add(linkTo(OrdrPosIfController.class).slash(ordrPosIf.getId()).withSelfRel());

	}
}
