package kr.co.paywith.pw.data.repository.od.staff;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class StaffResource extends Resource<Staff> {
	public StaffResource(Staff staff, Link... links) {
		super(staff, links);
		add(linkTo(StaffController.class).slash(staff.getId()).withSelfRel());

	}
}
