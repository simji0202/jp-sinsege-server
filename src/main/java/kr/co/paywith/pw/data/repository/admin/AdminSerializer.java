package kr.co.paywith.pw.data.repository.admin;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class AdminSerializer extends JsonSerializer<Admin> {

  @Override
  public void serialize(Admin admin, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {

    gen.writeStartObject();
    gen.writeNumberField("id", admin.getId());
    gen.writeStringField("adminId", admin.getAdminId());
    //      gen.writeStringField("adminNm", admin.getAdminNm());
    gen.writeEndObject();

  }
}
