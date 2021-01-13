package kr.co.paywith.pw.data.repository.mbs.goodsOpt;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class GoodsOptResource extends Resource<GoodsOpt> {
	public GoodsOptResource(GoodsOpt goodsOpt, Link... links) {
		super(goodsOpt, links);
		add(linkTo(GoodsOptController.class).slash(goodsOpt.getId()).withSelfRel());

	}
}
