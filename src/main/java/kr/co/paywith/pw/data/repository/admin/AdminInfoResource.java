package kr.co.paywith.pw.data.repository.admin;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class AdminInfoResource extends Resource<AdminResp> {

  public AdminInfoResource(AdminResp adminResp, Link... links) {
    super(adminResp, links);
    add(linkTo(AdminController.class).slash(adminResp.getId()).withSelfRel());

  }
}
