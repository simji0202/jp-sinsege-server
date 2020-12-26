package kr.co.paywith.pw.data.repository.od.dcMst;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class DcMstResource extends Resource<DcMst> {
	public DcMstResource(DcMst dcMst, Link... links) {
		super(dcMst, links);
		add(linkTo(DcMstController.class).slash(dcMst.getId()).withSelfRel());

	}
}
