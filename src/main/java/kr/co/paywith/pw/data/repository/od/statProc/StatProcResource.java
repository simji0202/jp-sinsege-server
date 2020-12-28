package kr.co.paywith.pw.data.repository.od.statProc;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class StatProcResource extends Resource<StatProc> {
	public StatProcResource(StatProc statProc, Link... links) {
		super(statProc, links);
		add(linkTo(StatProcController.class).slash(statProc.getId()).withSelfRel());

	}
}
