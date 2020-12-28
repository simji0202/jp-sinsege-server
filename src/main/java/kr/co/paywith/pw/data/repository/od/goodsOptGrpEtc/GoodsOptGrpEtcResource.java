package kr.co.paywith.pw.data.repository.od.goodsOptGrpEtc;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class GoodsOptGrpEtcResource extends Resource<GoodsOptGrpEtc> {
	public GoodsOptGrpEtcResource(GoodsOptGrpEtc goodsOptGrpEtc, Link... links) {
		super(goodsOptGrpEtc, links);
		add(linkTo(GoodsOptGrpEtcController.class).slash(goodsOptGrpEtc.getId()).withSelfRel());

	}
}
