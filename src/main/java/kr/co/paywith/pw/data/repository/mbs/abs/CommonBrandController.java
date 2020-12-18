package kr.co.paywith.pw.data.repository.mbs.abs;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "commonBrand")
public abstract class CommonBrandController {

    private static final Logger log = LoggerFactory.getLogger(CommonBrandController.class);

//    @Autowired
//    public BrandRepository brandRepository;

    @Autowired
    public ModelMapper modelMapper;

}
