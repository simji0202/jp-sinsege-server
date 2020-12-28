package kr.co.paywith.pw.data.repository.od.exceptMst;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class ExceptMstResource extends Resource<ExceptMst> {
	public ExceptMstResource(ExceptMst exceptMst, Link... links) {
		super(exceptMst, links);
		add(linkTo(ExceptMstController.class).slash(exceptMst.getId()).withSelfRel());

	}
}
