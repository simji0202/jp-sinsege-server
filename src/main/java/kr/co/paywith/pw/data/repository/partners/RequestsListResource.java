package kr.co.paywith.pw.data.repository.partners;

import kr.co.paywith.pw.data.repository.requests.RequestsList;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class RequestsListResource extends Resource<RequestsList> {

  public RequestsListResource(RequestsList requestsList, Link... links) {
    super(requestsList, links);
    add(linkTo(PartnersController.class).slash(requestsList.getId()).withSelfRel());
  }

}
