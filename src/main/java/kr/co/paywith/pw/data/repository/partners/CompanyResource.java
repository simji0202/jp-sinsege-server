package kr.co.paywith.pw.data.repository.partners;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class CompanyResource extends Resource<Company> {

  public CompanyResource(Company company, Link... links) {
    super(company, links);
    add(linkTo(kr.co.paywith.pw.data.repository.partners.CompanyController.class).slash(company.getId()).withSelfRel());
  }
}
