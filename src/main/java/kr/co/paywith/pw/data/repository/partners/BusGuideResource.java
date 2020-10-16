package kr.co.paywith.pw.data.repository.partners;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class BusGuideResource extends Resource<BusGuide> {

  public BusGuideResource(BusGuide busGuide, Link... links) {
    super(busGuide, links);
    add(linkTo(BusGuideController.class).slash(busGuide.getId()).withSelfRel());
  }
}
