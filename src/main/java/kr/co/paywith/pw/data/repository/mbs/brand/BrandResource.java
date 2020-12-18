package kr.co.paywith.pw.data.repository.mbs.brand;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class BrandResource extends Resource<Brand> {

  public BrandResource(Brand brand, Link... links) {
    super(brand, links);
    add(linkTo(BrandController.class).slash(brand.getId()).withSelfRel());

  }
}
