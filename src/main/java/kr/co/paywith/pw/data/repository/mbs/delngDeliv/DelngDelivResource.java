package kr.co.paywith.pw.data.repository.mbs.delngDeliv;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class DelngDelivResource extends Resource<DelngDeliv> {

  public DelngDelivResource(DelngDeliv delngDeliv, Link... links) {
    super(delngDeliv, links);
    add(linkTo(DelngDelivController.class).slash(delngDeliv.getId()).withSelfRel());

  }
}
