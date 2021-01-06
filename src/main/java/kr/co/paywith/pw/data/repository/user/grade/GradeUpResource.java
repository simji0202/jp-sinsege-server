package kr.co.paywith.pw.data.repository.user.grade;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class GradeUpResource extends Resource<GradeUp> {

    public GradeUpResource(GradeUp gradeUp, Link... links) {
        super(gradeUp, links);
        add(linkTo(GradeUpController.class).slash(gradeUp.getId()).withSelfRel());

    }
}
