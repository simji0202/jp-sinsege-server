package kr.co.paywith.pw.data.repository.mbs.cpnMaster;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CpnMasterSerializer extends JsonSerializer<CpnMaster> {

    @Override
    public void serialize(CpnMaster value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {

        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("cpnNm", value.getCpnNm());
        gen.writeStringField("cpnCd", value.getCpnCd());

        gen.writeEndObject();

    }
}
