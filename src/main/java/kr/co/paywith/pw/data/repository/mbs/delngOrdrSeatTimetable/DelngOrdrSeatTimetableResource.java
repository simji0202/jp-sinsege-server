package kr.co.paywith.pw.data.repository.mbs.delngOrdrSeatTimetable;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class DelngOrdrSeatTimetableResource extends Resource<DelngOrdrSeatTimetable> {
	public DelngOrdrSeatTimetableResource(DelngOrdrSeatTimetable delngOrdrSeatTimetable, Link... links) {
		super(delngOrdrSeatTimetable, links);
		add(linkTo(DelngOrdrSeatTimetableController.class).slash(delngOrdrSeatTimetable.getId()).withSelfRel());

	}
}
