package kr.co.paywith.pw.data.repository.user.grade;

import kr.co.paywith.pw.data.repository.mbs.brand.BrandController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class GradeResource extends Resource<Grade> {

    public GradeResource(Grade grade, Link... links) {
        super(grade, links);
        add(linkTo(GradeController.class).slash(grade.getId()).withSelfRel());

    }
}
