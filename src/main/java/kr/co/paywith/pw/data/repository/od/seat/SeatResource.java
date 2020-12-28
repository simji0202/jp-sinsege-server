package kr.co.paywith.pw.data.repository.od.seat;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class SeatResource extends Resource<Seat> {
	public SeatResource(Seat seat, Link... links) {
		super(seat, links);
		add(linkTo(SeatController.class).slash(seat.getId()).withSelfRel());

	}
}
