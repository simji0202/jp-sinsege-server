package kr.co.paywith.pw.data.repository.od.ordrGoodsOpt;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class OrdrGoodsOptResource extends Resource<OrdrGoodsOpt> {
	public OrdrGoodsOptResource(OrdrGoodsOpt ordrGoodsOpt, Link... links) {
		super(ordrGoodsOpt, links);
		add(linkTo(OrdrGoodsOptController.class).slash(ordrGoodsOpt.getId()).withSelfRel());

	}
}
