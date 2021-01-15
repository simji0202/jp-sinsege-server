package kr.co.paywith.pw.data.repository.mbs.goodsGrp;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import kr.co.paywith.pw.data.repository.mbs.goodsGrpgrp.GoodsGrpController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class GoodsGrpResource extends Resource<GoodsGrp> {

    public GoodsGrpResource(GoodsGrp goodsGrp, Link... links) {
        super(goodsGrp, links);
        add(linkTo(GoodsGrpController.class).slash(goodsGrp.getId()).withSelfRel());

    }
}
