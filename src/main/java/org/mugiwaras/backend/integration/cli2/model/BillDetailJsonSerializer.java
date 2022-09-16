package org.mugiwaras.backend.integration.cli2.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class BillDetailJsonSerializer extends StdSerializer<BillDetail> {
    public BillDetailJsonSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    @Override
    public void serialize(BillDetail billDetail, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idBill", billDetail.getId().idBill);
        jsonGenerator.writeNumberField("idProduct", billDetail.getId().idProductCli2);
        jsonGenerator.writeNumberField("price", billDetail.getPrecio());
        jsonGenerator.writeNumberField("cantidad", billDetail.getCantidad());
        jsonGenerator.writeObjectField("product",billDetail.getProduct());
        jsonGenerator.writeEndObject();

    }
}
