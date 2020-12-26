package kr.co.paywith.pw.data.repository.mbs.msgTemplate;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class MsgTemplateResource extends Resource<MsgTemplate> {
	public MsgTemplateResource(MsgTemplate msgTemplate, Link... links) {
		super(msgTemplate, links);
		add(linkTo(MsgTemplateController.class).slash(msgTemplate.getId()).withSelfRel());

	}
}
