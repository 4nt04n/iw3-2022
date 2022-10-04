package org.mugiwaras.backend.integration.cli2.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.mugiwaras.backend.controllers.BaseRestController;
import org.mugiwaras.backend.controllers.Constants;
import org.mugiwaras.backend.integration.cli2.model.Audit;
import org.mugiwaras.backend.integration.cli2.model.AuditJsonSerializer;
import org.mugiwaras.backend.integration.cli2.model.business.IAuditBusiness;
import org.mugiwaras.backend.model.business.BusinessException;
import org.mugiwaras.backend.util.IStandartResponseBusiness;
import org.mugiwaras.backend.util.JsonUtiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.URL_AUDIT)
public class AuditRestController extends BaseRestController {

    @Autowired
    private IStandartResponseBusiness response;

    @Autowired
    private IAuditBusiness auditBusiness;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list() {

        try {
            StdSerializer<Audit> serializer = new AuditJsonSerializer(Audit.class, false);
            String result = JsonUtiles.getObjectMapper(Audit.class, serializer, null).writeValueAsString(auditBusiness.list(getUserLogged().getUsername()));

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
