package kr.co.paywith.pw.data.repository.od.timesale;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class TimesaleResource extends Resource<Timesale> {
	public TimesaleResource(Timesale timesale, Link... links) {
		super(timesale, links);
		add(linkTo(TimesaleController.class).slash(timesale.getId()).withSelfRel());

	}
}
