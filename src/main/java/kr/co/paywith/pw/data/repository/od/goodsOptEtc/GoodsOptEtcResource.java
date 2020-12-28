package kr.co.paywith.pw.data.repository.od.goodsOptEtc;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class GoodsOptEtcResource extends Resource<GoodsOptEtc> {
	public GoodsOptEtcResource(GoodsOptEtc goodsOptEtc, Link... links) {
		super(goodsOptEtc, links);
		add(linkTo(GoodsOptEtcController.class).slash(goodsOptEtc.getId()).withSelfRel());

	}
}
