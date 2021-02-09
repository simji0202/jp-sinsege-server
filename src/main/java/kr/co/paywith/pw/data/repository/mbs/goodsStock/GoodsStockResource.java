package kr.co.paywith.pw.data.repository.mbs.goodsStock;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class GoodsStockResource extends Resource<GoodsStock> {

    public GoodsStockResource(GoodsStock goodsStock, Link... links) {
        super(goodsStock, links);
        add(linkTo(GoodsStockController.class).slash(goodsStock.getId()).withSelfRel());

    }
}
