package org.mugiwaras.backend.integration.cli2.model;

import static java.time.temporal.ChronoUnit.DAYS;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.mugiwaras.backend.util.JsonUtiles;

public class BillSlimV1JsonSerializer extends StdSerializer<Bill> {

    private static final long serialVersionUID = -3706327488880784297L;

    public BillSlimV1JsonSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    @Override
    public void serialize(Bill value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("version", "v1");
        gen.writeNumberField("id", value.getIdBill());
        gen.writeNumberField("number", value.getNumber());
        gen.writeStringField("broadcastDate", value.getBroadcastDate().toString());
        gen.writeStringField("expirationDate", value.getExpirationDate().toString());
        gen.writeBooleanField("canceled", value.isCanceled());

        //Calculate the total price of all the details
        double totalCost=0;
        for (BillDetail item : value.getDetalle()) {
            totalCost+= item.getCantidad() * item.getPrecio();
        }
        gen.writeNumberField("totalDetail", totalCost);

//        gen.writeNumberField("daysExpired", DAYS.between(Instant.ofEpochMilli(value.getExpirationDate().getTime())
//                .atZone(ZoneId.systemDefault()).toLocalDateTime(), LocalDateTime.now()));


//        String componentsStr = JsonUtiles
//                .getObjectMapper(BillDetail.class, new BillDetailJsonSerializer(BillDetail.class, false), null)
//                .writeValueAsString(value.getDetalle());
     //   gen.writeFieldName("details");
//        gen.writeRawValue(componentsStr);

        gen.writeEndObject();

    }

}