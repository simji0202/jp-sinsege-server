package kr.co.paywith.pw.data.repository.mbs.abs;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "commonMngr")
public abstract class CommonMngrController {

  private static final Logger log = LoggerFactory.getLogger(CommonMngrController.class);

  @Autowired
  public ModelMapper modelMapper;

  @Autowired
  public ModelMapper nullModelMapper;


}
