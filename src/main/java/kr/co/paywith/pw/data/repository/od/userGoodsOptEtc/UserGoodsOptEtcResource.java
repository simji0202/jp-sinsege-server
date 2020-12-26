package kr.co.paywith.pw.data.repository.od.userGoodsOptEtc;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class UserGoodsOptEtcResource extends Resource<UserGoodsOptEtc> {
	public UserGoodsOptEtcResource(UserGoodsOptEtc userGoodsOptEtc, Link... links) {
		super(userGoodsOptEtc, links);
		add(linkTo(UserGoodsOptEtcController.class).slash(userGoodsOptEtc.getId()).withSelfRel());

	}
}
