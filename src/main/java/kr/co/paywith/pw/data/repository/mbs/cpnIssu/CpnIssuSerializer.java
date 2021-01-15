package kr.co.paywith.pw.data.repository.mbs.cpnIssu;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CpnIssuSerializer extends JsonSerializer<CpnIssu> {

    @Override
    public void serialize(CpnIssu value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {

        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("cpnIssuNm", value.getCpnIssuNm());
     //   gen.writeStringField("cpnNm", value.getCpnMaster().getCpnNm());

        gen.writeEndObject();

    }
}
