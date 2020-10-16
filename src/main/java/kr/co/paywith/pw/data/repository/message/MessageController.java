package kr.co.paywith.pw.data.repository.message;


import io.swagger.annotations.Api;
import javax.validation.Valid;
import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.data.repository.admin.Admin;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/message", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@Api(value = "messageController", description = "message", basePath = "/api/message")
public class MessageController {


  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private MessageValidator messageValidator;

  @Autowired
  private MsgRuleRepository msgRuleRepository;

  @Autowired
  private MsgTemplateRepository msgTemplateRepository;


  @PostMapping("/msgrule")
  private ResponseEntity insertMsgRule(@RequestBody @Valid MsgRuleDto msgRuleDto,
      Errors errors,
      @CurrentUser Admin currentUser) {
    // 입력 값 체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 논리적 오류 (제약조건) 체크
    messageValidator.validate(msgRuleDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    MsgRule msgRule = modelMapper.map(msgRuleDto, MsgRule.class);

    MsgRule newMsgRule = this.msgRuleRepository.save(msgRule);

    return ResponseEntity.ok(newMsgRule);

  }


  @PostMapping("/msgtemp")
  private ResponseEntity insertMsgTemp(@RequestBody @Valid MsgTemplateDto msgTemplateDto,
      Errors errors,
      @CurrentUser Admin currentUser) {
    // 입력 값 체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 논리적 오류 (제약조건) 체크
    messageValidator.validate(msgTemplateDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    MsgTemplate msgTemplate = modelMapper.map(msgTemplateDto, MsgTemplate.class);

    MsgTemplate newMsgTemplate = this.msgTemplateRepository.save(msgTemplate);

    return ResponseEntity.ok(newMsgTemplate);

  }


  private ResponseEntity badRequest(Errors errors) {
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
  }
}



