package kr.co.paywith.pw.data.repository.mbs.goods;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class GoodsResource extends Resource<Goods> {

    public GoodsResource(Goods goods, Link... links) {
        super(goods, links);
        add(linkTo(GoodsController.class).slash(goods.getId()).withSelfRel());

    }
}
