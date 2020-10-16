package kr.co.paywith.pw.data.repository.schedule;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class ScheduleResource extends Resource<Schedule> {
	public ScheduleResource(Schedule schedule, Link... links) {
		super(schedule, links);
		add(linkTo(ScheduleController.class).slash(schedule.getId()).withSelfRel());

	}
}
