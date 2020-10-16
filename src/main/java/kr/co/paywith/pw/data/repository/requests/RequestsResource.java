package kr.co.paywith.pw.data.repository.requests;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class RequestsResource extends Resource<Requests> {

  public RequestsResource(Requests requests, Link... links) {
    super(requests, links);
    add(linkTo(RequestsController.class).slash(requests.getId()).withSelfRel());
  }
}
