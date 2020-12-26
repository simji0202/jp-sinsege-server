package kr.co.paywith.pw.data.repository.od.seatUse;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class SeatUseResource extends Resource<SeatUse> {
	public SeatUseResource(SeatUse seatUse, Link... links) {
		super(seatUse, links);
		add(linkTo(SeatUseController.class).slash(seatUse.getId()).withSelfRel());

	}
}
