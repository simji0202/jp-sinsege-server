package kr.co.paywith.pw.data.repository.mbs.file;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class FileResource extends Resource<File> {
	public FileResource(File file, Link... links) {
		super(file, links);
		add(linkTo(FileController.class).slash(file.getId()).withSelfRel());

	}
}
