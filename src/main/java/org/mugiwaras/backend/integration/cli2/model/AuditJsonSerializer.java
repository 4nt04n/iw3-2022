package org.mugiwaras.backend.integration.cli2.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class AuditJsonSerializer extends StdSerializer<Audit> {
    public AuditJsonSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    @Override
    public void serialize(Audit audit, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("fechaOperacion", audit.getFecha().toString());
        jsonGenerator.writeStringField("tipoOperacion", audit.getOperacion());
        jsonGenerator.writeNumberField("idOriginal", audit.getIdOriginal());
        jsonGenerator.writeStringField("username", audit.getUser());
        jsonGenerator.writeEndObject();

    }
}

