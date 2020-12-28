package kr.co.paywith.pw.data.repository.od.userGoodsOpt;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class UserGoodsOptResource extends Resource<UserGoodsOpt> {
	public UserGoodsOptResource(UserGoodsOpt userGoodsOpt, Link... links) {
		super(userGoodsOpt, links);
		add(linkTo(UserGoodsOptController.class).slash(userGoodsOpt.getId()).withSelfRel());

	}
}
