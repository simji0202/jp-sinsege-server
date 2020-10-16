package kr.co.paywith.pw.data.repository.staistics;


import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.mapper.StatisticsMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Service
public class StatisticsService {



  @Autowired
  ModelMapper modelMapper;

  @Autowired
  StatisticsMapper statisticsMapper;


  /**
   *
   */
  @Transactional
  public FrontStatisticsDto getFrontStatics() {

    FrontStatisticsDto frontStatisticsDto = statisticsMapper.selectFrontStatics();

    return frontStatisticsDto;
  }



}
