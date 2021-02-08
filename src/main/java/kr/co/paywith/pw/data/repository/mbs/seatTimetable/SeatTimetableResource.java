package kr.co.paywith.pw.data.repository.mbs.seatTimetable;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class SeatTimetableResource extends Resource<SeatTimetable> {
	public SeatTimetableResource(SeatTimetable seatTimetable, Link... links) {
		super(seatTimetable, links);
		add(linkTo(SeatTimetableController.class).slash(seatTimetable.getId()).withSelfRel());

	}
}
