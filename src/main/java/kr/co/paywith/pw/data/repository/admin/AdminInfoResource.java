package kr.co.paywith.pw.data.repository.admin;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class AdminInfoResource extends Resource<AdminInfo> {

  public AdminInfoResource(AdminInfo adminInfo, Link... links) {
    super(adminInfo, links);
    add(linkTo(AdminController.class).slash(adminInfo.getId()).withSelfRel());

  }
}
