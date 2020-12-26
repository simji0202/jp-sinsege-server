package kr.co.paywith.pw.data.repository.od.cpnMst;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class CpnMstResource extends Resource<CpnMst> {
	public CpnMstResource(CpnMst cpnMst, Link... links) {
		super(cpnMst, links);
		add(linkTo(CpnMstController.class).slash(cpnMst.getId()).withSelfRel());

	}
}
