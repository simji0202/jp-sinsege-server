package kr.co.paywith.pw.data.repository.od.seatSch;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class SeatSchResource extends Resource<SeatSch> {
	public SeatSchResource(SeatSch seatSch, Link... links) {
		super(seatSch, links);
		add(linkTo(SeatSchController.class).slash(seatSch.getId()).withSelfRel());

	}
}
