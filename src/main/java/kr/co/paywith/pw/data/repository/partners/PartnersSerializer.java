package kr.co.paywith.pw.data.repository.partners;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class PartnersSerializer extends JsonSerializer<Partners> {

  @Override
  public void serialize(Partners value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {

    gen.writeStartObject();
    gen.writeNumberField("id", value.getId());
    gen.writeStringField("name", value.getName());
    gen.writeObjectField("status", value.getStatus());
    gen.writeStringField("phone", value.getPhone());
    gen.writeStringField("logoImgUrl", value.getLogoImgUrl());
    gen.writeEndObject();

  }
}
