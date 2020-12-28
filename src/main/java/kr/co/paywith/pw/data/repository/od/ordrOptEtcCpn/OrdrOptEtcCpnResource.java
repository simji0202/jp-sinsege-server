package kr.co.paywith.pw.data.repository.od.ordrOptEtcCpn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class OrdrOptEtcCpnResource extends Resource<OrdrOptEtcCpn> {
	public OrdrOptEtcCpnResource(OrdrOptEtcCpn ordrOptEtcCpn, Link... links) {
		super(ordrOptEtcCpn, links);
		add(linkTo(OrdrOptEtcCpnController.class).slash(ordrOptEtcCpn.getId()).withSelfRel());

	}
}
