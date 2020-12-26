package kr.co.paywith.pw.data.repository.od.tokenMng;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class TokenMngResource extends Resource<TokenMng> {
	public TokenMngResource(TokenMng tokenMng, Link... links) {
		super(tokenMng, links);
		add(linkTo(TokenMngController.class).slash(tokenMng.getId()).withSelfRel());

	}
}
