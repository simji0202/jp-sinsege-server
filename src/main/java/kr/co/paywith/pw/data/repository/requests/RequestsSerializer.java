package kr.co.paywith.pw.data.repository.requests;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class RequestsSerializer extends JsonSerializer<Requests> {

  @Override
  public void serialize(Requests value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {

    gen.writeStartObject();
    gen.writeNumberField("id", value.getId());
//    gen.writeStringField("name", value.getName());
//    gen.writeStringField("phone", value.getPhone());
//    gen.writeStringField("wishMoveDate", value.getWishMoveDate());

    gen.writeEndObject();

  }
}
