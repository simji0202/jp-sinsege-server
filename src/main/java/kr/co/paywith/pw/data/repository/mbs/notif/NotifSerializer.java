package kr.co.paywith.pw.data.repository.mbs.notif;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import kr.co.paywith.pw.data.repository.mbs.notif.Notif;

public class NotifSerializer extends JsonSerializer<Notif> {

    @Override
    public void serialize(Notif value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {

        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("notifSj", value.getNotifSj());
     //   gen.writeStringField("cpnNm", value.getCpnMaster().getCpnNm());

        gen.writeEndObject();

    }
}
