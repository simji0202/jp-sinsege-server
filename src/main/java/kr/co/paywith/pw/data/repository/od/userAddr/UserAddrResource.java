package kr.co.paywith.pw.data.repository.od.userAddr;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class UserAddrResource extends Resource<UserAddr> {
	public UserAddrResource(UserAddr userAddr, Link... links) {
		super(userAddr, links);
		add(linkTo(UserAddrController.class).slash(userAddr.getId()).withSelfRel());

	}
}
