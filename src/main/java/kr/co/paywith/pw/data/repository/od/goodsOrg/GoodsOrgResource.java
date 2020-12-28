package kr.co.paywith.pw.data.repository.od.goodsOrg;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class GoodsOrgResource extends Resource<GoodsOrg> {
	public GoodsOrgResource(GoodsOrg goodsOrg, Link... links) {
		super(goodsOrg, links);
		add(linkTo(GoodsOrgController.class).slash(goodsOrg.getId()).withSelfRel());

	}
}
