package kr.co.paywith.pw.data.repository.od.ordrCpn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class OrdrCpnResource extends Resource<OrdrCpn> {
	public OrdrCpnResource(OrdrCpn ordrCpn, Link... links) {
		super(ordrCpn, links);
		add(linkTo(OrdrCpnController.class).slash(ordrCpn.getId()).withSelfRel());

	}
}
