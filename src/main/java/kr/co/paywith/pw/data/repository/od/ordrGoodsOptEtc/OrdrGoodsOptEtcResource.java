package kr.co.paywith.pw.data.repository.od.ordrGoodsOptEtc;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class OrdrGoodsOptEtcResource extends Resource<OrdrGoodsOptEtc> {
	public OrdrGoodsOptEtcResource(OrdrGoodsOptEtc ordrGoodsOptEtc, Link... links) {
		super(ordrGoodsOptEtc, links);
		add(linkTo(OrdrGoodsOptEtcController.class).slash(ordrGoodsOptEtc.getId()).withSelfRel());

	}
}
