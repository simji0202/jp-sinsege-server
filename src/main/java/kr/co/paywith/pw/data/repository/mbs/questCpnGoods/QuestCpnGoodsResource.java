package kr.co.paywith.pw.data.repository.mbs.questCpnGoods;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class QuestCpnGoodsResource extends Resource<QuestCpnGoods> {
	public QuestCpnGoodsResource(QuestCpnGoods questCpnGoods, Link... links) {
		super(questCpnGoods, links);
		add(linkTo(QuestCpnGoodsController.class).slash(questCpnGoods.getId()).withSelfRel());

	}
}
