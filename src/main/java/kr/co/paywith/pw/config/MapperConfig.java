package kr.co.paywith.pw.config;


import kr.co.paywith.pw.data.repository.enumeration.AuthType;
import kr.co.paywith.pw.data.repository.enumeration.AvailServiceType;
import kr.co.paywith.pw.data.repository.enumeration.BbsType;
import kr.co.paywith.pw.data.repository.enumeration.BrandFnType;
import kr.co.paywith.pw.data.repository.enumeration.ChrgType;
import kr.co.paywith.pw.data.repository.enumeration.CpnIssuRuleType;
import kr.co.paywith.pw.data.repository.enumeration.CpnSttsType;
import kr.co.paywith.pw.data.repository.enumeration.CpnType;
import kr.co.paywith.pw.data.repository.enumeration.DtType;
import kr.co.paywith.pw.data.repository.enumeration.EnumMapper;
import kr.co.paywith.pw.data.repository.enumeration.GoodsGrpType;
import kr.co.paywith.pw.data.repository.enumeration.MenuType;
import kr.co.paywith.pw.data.repository.enumeration.PgType;
import kr.co.paywith.pw.data.repository.enumeration.PointCutType;
import kr.co.paywith.pw.data.repository.enumeration.PointHistType;
import kr.co.paywith.pw.data.repository.enumeration.PointRsrvRuleType;
import kr.co.paywith.pw.data.repository.enumeration.PosFnType;
import kr.co.paywith.pw.data.repository.enumeration.PosType;
import kr.co.paywith.pw.data.repository.enumeration.ScoreRuleType;
import kr.co.paywith.pw.data.repository.enumeration.SendSttsType;
import kr.co.paywith.pw.data.repository.enumeration.UserAppOsType;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration()
        .setAmbiguityIgnored(true)
        .setSkipNullEnabled(true)
        .setMatchingStrategy(MatchingStrategies.STRICT);
    return modelMapper;
  }

  @Bean
  public ModelMapper nullModelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration()
        .setAmbiguityIgnored(true)
        .setSkipNullEnabled(false)
        .setMatchingStrategy(MatchingStrategies.STRICT);
    return modelMapper;
  }

  @Bean
  public EnumMapper enumMapper() {
    EnumMapper enumMapper = new EnumMapper();
    enumMapper.put("authType", AuthType.class);
    enumMapper.put("availServiceType", AvailServiceType.class);
    enumMapper.put("bbsType", BbsType.class);
    enumMapper.put("chrgType", ChrgType.class);
//        enumMapper.put("chrgSetleMthdCd", ChrgSetleMthdCd.class);
//        enumMapper.put("chrgSetleSttsCd", ChrgSetleSttsCd.class);
    enumMapper.put("cpnIssuRuleType", CpnIssuRuleType.class);
    enumMapper.put("cpnSttsType", CpnSttsType.class);
    enumMapper.put("cpnType", CpnType.class);
    enumMapper.put("dtType", DtType.class);
    enumMapper.put("goodsGrpType", GoodsGrpType.class);
//        enumMapper.put("gradeUpRuleType", GradeUpRuleType.class);
    enumMapper.put("menuType", MenuType.class);
//        enumMapper.put("msgHistSttsType", MsgHistSttsType.class);
//        enumMapper.put("msgRuleType", MsgRuleType.class);
//        enumMapper.put("msgType", MsgType.class);
    enumMapper.put("pgType", PgType.class);
    enumMapper.put("pointCutType", PointCutType.class);
    enumMapper.put("pointHistType", PointHistType.class);
    enumMapper.put("pointRsrvRuleType", PointRsrvRuleType.class);
    enumMapper.put("posFnType", PosFnType.class);
    enumMapper.put("posType", PosType.class);
//        enumMapper.put("prpayValidPeroidCd", DtType.class);
    enumMapper.put("scoreRuleType", ScoreRuleType.class);
    enumMapper.put("sendSttsType", SendSttsType.class);
    enumMapper.put("stampValidPeriodCd", DtType.class);
    enumMapper.put("userAppOsType", UserAppOsType.class);
//        enumMapper.put("useTypeCd", UseTypeCd.class);
    enumMapper.put("brandFnType", BrandFnType.class);
//        enumMapper.put("duplicateAvailFieldCd", DuplicateAvailFieldCd.class);

    return enumMapper;
  }

//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper;
//    }
}
