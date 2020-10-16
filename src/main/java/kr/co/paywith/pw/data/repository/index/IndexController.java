package kr.co.paywith.pw.data.repository.index;


import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class IndexController {

  @GetMapping("/api/")
  public ResourceSupport index() {
    var index = new ResourceSupport();

    return index;
  }

}
