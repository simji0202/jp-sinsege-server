package kr.co.paywith.pw.data.repository.admin;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class AdminResource extends Resource<Admin> {

  public AdminResource(Admin admin, Link... links) {
    super(admin, links);
    add(linkTo(AdminController.class).slash(admin.getId()).withSelfRel());

  }
}
