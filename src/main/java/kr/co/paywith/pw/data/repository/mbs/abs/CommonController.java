package kr.co.paywith.pw.data.repository.mbs.abs;


import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component(value = "mbsAbsCommonController")
public abstract class CommonController {

//  @Autowired
//  public BrandRepository brandRepository;
//
//  @Autowired
//  public BrandService brandService;
//
//  @Autowired
//  public AdminRepository adminRepository;

  @Autowired
  public ModelMapper modelMapper;

  @Autowired
  public ModelMapper nullModelMapper;

}
