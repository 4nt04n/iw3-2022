package org.mugiwaras.backend.integration.cli2.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.mugiwaras.backend.util.JsonUtiles;

import java.io.IOException;

public class BillSlimV2JsonSerializer extends StdSerializer<Bill> {

    private static final long serialVersionUID = -3706327488880784297L;

    public BillSlimV2JsonSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    @Override
    public void serialize(Bill value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        gen.writeStringField("version", "v2");

//        gen.writeNumberField("daysExpired", DAYS.between(Instant.ofEpochMilli(value.getExpirationDate().getTime())
//                .atZone(ZoneId.systemDefault()).toLocalDateTime(), LocalDateTime.now()));


        String componentsStr = JsonUtiles
                .getObjectMapper(BillDetail.class, new BillDetailJsonSerializer(BillDetail.class, false), null)
                .writeValueAsString(value.getDetalle());
        gen.writeFieldName("details");
        gen.writeRawValue(componentsStr);

        gen.writeEndObject();

    }


}
