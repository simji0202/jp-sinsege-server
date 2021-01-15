package kr.co.paywith.pw.data.repository.mbs.cm;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class CpnMasterResource extends Resource<CpnMaster> {

    public CpnMasterResource(CpnMaster cpnMaster, Link... links) {
        super(cpnMaster, links);
        add(linkTo(CpnMasterController.class).slash(cpnMaster.getId()).withSelfRel());

    }
}
