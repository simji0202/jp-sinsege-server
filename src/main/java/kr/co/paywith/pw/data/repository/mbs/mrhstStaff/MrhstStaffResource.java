package kr.co.paywith.pw.data.repository.mbs.mrhstStaff;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class MrhstStaffResource extends Resource<MrhstStaff> {
	public MrhstStaffResource(MrhstStaff mrhstStaff, Link... links) {
		super(mrhstStaff, links);
		add(linkTo(MrhstStaffController.class).slash(mrhstStaff.getId()).withSelfRel());

	}
}
