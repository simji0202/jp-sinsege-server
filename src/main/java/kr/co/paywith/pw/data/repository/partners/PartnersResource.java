package kr.co.paywith.pw.data.repository.partners;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class PartnersResource extends Resource<Partners> {

  public PartnersResource(Partners partners, Link... links) {
    super(partners, links);
    add(linkTo(PartnersController.class).slash(partners.getId()).withSelfRel());
  }
}
