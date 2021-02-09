package kr.co.paywith.pw.data.repository.mbs.goodsStockHist;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class GoodsStockHistResource extends Resource<GoodsStockHist> {

    public GoodsStockHistResource(GoodsStockHist goodsStockHist, Link... links) {
        super(goodsStockHist, links);
        add(linkTo(GoodsStockHistController.class).slash(goodsStockHist.getId()).withSelfRel());

    }
}
