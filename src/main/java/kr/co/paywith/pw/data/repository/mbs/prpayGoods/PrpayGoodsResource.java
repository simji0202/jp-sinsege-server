package kr.co.paywith.pw.data.repository.mbs.prpayGoods;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class PrpayGoodsResource extends Resource<PrpayGoods> {
	public PrpayGoodsResource(PrpayGoods prpayGoods, Link... links) {
		super(prpayGoods, links);
		add(linkTo(PrpayGoodsController.class).slash(prpayGoods.getId()).withSelfRel());

	}
}
