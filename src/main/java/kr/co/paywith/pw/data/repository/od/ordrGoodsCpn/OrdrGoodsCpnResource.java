package kr.co.paywith.pw.data.repository.od.ordrGoodsCpn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class OrdrGoodsCpnResource extends Resource<OrdrGoodsCpn> {
	public OrdrGoodsCpnResource(OrdrGoodsCpn ordrGoodsCpn, Link... links) {
		super(ordrGoodsCpn, links);
		add(linkTo(OrdrGoodsCpnController.class).slash(ordrGoodsCpn.getId()).withSelfRel());

	}
}
