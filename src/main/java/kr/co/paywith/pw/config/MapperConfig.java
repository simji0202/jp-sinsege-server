package kr.co.paywith.pw.config;


import kr.co.paywith.pw.data.repository.enumeration.*;
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
        enumMapper.put("authCd", AuthCd.class);
        enumMapper.put("availServiceCd", AvailServiceCd.class);
        enumMapper.put("bbsTypeCd", BbsTypeCd.class);
        enumMapper.put("chrgSetleChnlCd", ChrgTypeCd.class);
        enumMapper.put("chrgSetleMthdCd", ChrgSetleMthdCd.class);
        enumMapper.put("chrgSetleSttsCd", ChrgSetleSttsCd.class);
        enumMapper.put("cpnIssuRuleCd", CpnIssuRuleCd.class);
        enumMapper.put("cpnSttsCd", CpnSttsCd.class);
        enumMapper.put("cpnTypeCd", CpnTypeCd.class);
        enumMapper.put("dtTypeCd", DtTypeCd.class);
        enumMapper.put("goodsGrpCd", GoodsGrpCd.class);
        enumMapper.put("gradeUpRuleCd", GradeUpRuleCd.class);
        enumMapper.put("menuItemCd", MenuItemCd.class);
        enumMapper.put("msgHistSttsCd", MsgHistSttsCd.class);
        enumMapper.put("msgRuleCd", MsgRuleCd.class);
        enumMapper.put("msgTypeCd", MsgTypeCd.class);
        enumMapper.put("pgTypeCd", PgTypeCd.class);
        enumMapper.put("pointCutTypeCd", PointCutTypeCd.class);
        enumMapper.put("pointHistCd", PointHistCd.class);
        enumMapper.put("pointRuleTypeCd", PointRuleTypeCd.class);
        enumMapper.put("posAvailFnCd", PosAvailFnCd.class);
        enumMapper.put("posTypeCd", PosTypeCd.class);
        enumMapper.put("prpayOperCd", PrpayOperCd.class);
        enumMapper.put("prpayOvrRuleCd", PrpayOvrRuleCd.class);
        enumMapper.put("prpaySttsCd", PrpaySttsCd.class);
        enumMapper.put("prpayValidPeroidCd", DtTypeCd.class);
        enumMapper.put("scoreRuleTypeCd", ScoreRuleTypeCd.class);
        enumMapper.put("sendSttsCd", SendSttsCd.class);
        enumMapper.put("stampValidPeriodCd", DtTypeCd.class);
        enumMapper.put("userAppOsCd", UserAppOsCd.class);
        enumMapper.put("useTypeCd", UseTypeCd.class);
        enumMapper.put("availBrandFnCd", AvailBrandFnCd.class);
        enumMapper.put("duplicateAvailFieldCd", DuplicateAvailFieldCd.class);

        return enumMapper;
    }

//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper;
//    }
}
