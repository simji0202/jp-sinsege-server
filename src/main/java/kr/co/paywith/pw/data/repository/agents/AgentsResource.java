package kr.co.paywith.pw.data.repository.agents;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class AgentsResource extends Resource<Agents> {

  public AgentsResource(Agents agents, Link... links) {
    super(agents, links);
    add(linkTo(AgentsController.class).slash(agents.getId()).withSelfRel());

  }
}
