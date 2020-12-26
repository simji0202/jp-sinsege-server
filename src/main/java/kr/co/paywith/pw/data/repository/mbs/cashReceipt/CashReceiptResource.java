package kr.co.paywith.pw.data.repository.mbs.cashReceipt;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class CashReceiptResource extends Resource<CashReceipt> {
	public CashReceiptResource(CashReceipt cashReceipt, Link... links) {
		super(cashReceipt, links);
		add(linkTo(CashReceiptController.class).slash(cashReceipt.getId()).withSelfRel());

	}
}
