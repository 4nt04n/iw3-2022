package org.mugiwaras.backend.integration.cli2.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.mugiwaras.backend.controllers.BaseRestController;
import org.mugiwaras.backend.controllers.Constants;
import org.mugiwaras.backend.integration.cli2.model.Audit;
import org.mugiwaras.backend.integration.cli2.model.Bill;
import org.mugiwaras.backend.integration.cli2.model.BillSlimV1JsonSerializer;
import org.mugiwaras.backend.integration.cli2.model.BillSlimV2JsonSerializer;
import org.mugiwaras.backend.integration.cli2.model.business.IAuditBusiness;
import org.mugiwaras.backend.integration.cli2.model.business.IBillBusiness;
import org.mugiwaras.backend.integration.cli2.model.business.IBillDetailBusiness;
import org.mugiwaras.backend.model.business.BusinessException;
import org.mugiwaras.backend.model.business.FoundException;
import org.mugiwaras.backend.model.business.NotFoundException;
import org.mugiwaras.backend.util.IStandartResponseBusiness;
import org.mugiwaras.backend.util.JsonUtiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping(Constants.URL_INTEGRATION_CLI2_BILLS)
public class BillRestController extends BaseRestController {
    @Autowired
    private IStandartResponseBusiness response;

    @Autowired
    private IBillBusiness billBusiness;

    @Autowired
    private IBillDetailBusiness billDetailBusiness;

    @Autowired
    private IAuditBusiness auditBusiness;


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(@RequestParam(name = "slim", required = false, defaultValue = "v0") String slimVersion) {

        try {
            StdSerializer<Bill> ser = null;
            if (slimVersion.equalsIgnoreCase("v1")) {
                ser = new BillSlimV1JsonSerializer(Bill.class, false);
            } else if (slimVersion.equalsIgnoreCase("v2")) {
                ser = new BillSlimV2JsonSerializer(Bill.class, false);
            } else {
                return new ResponseEntity<>(billBusiness.list(), HttpStatus.OK); //vO
            }
            String result = JsonUtiles.getObjectMapper(Bill.class, ser, null).writeValueAsString(billBusiness.list());

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (BusinessException | JsonProcessingException e) {
            return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/canceled", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listNoNull() {
        try {
            return new ResponseEntity<>(billBusiness.listNoNull(), HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loadByNumber(@RequestParam(name = "slim", required = false, defaultValue = "v0") String slimVersion, @PathVariable("number") Long number) {
        try {
            StdSerializer<Bill> ser = null;
            if (slimVersion.equalsIgnoreCase("v1")) {
                ser = new BillSlimV1JsonSerializer(Bill.class, false);
                String result = JsonUtiles.getObjectMapper(Bill.class, ser, null).writeValueAsString(billBusiness.loadByNumberV1(number));
                return new ResponseEntity<>(result, HttpStatus.OK);//return v1

            } else if (slimVersion.equalsIgnoreCase("v2")) {
                ser = new BillSlimV2JsonSerializer(Bill.class, false);
                String result = JsonUtiles.getObjectMapper(Bill.class, ser, null).writeValueAsString(billBusiness.loadByNumberV2(number));
                return new ResponseEntity<>(result, HttpStatus.OK);//return v2
            } else {
                return new ResponseEntity<>(billBusiness.loadByNumberV1(number), HttpStatus.OK); //return v0
            }

        } catch (BusinessException e) {
            return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException | JsonProcessingException e) {
            return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "cancel/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancel(@PathVariable("number") Long number) throws BusinessException, NotFoundException {
        try {
            return new ResponseEntity<>(billBusiness.setCancel(billBusiness.loadByNumberV1(number)), HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "cancel/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancelById(@PathVariable("id") Long id) throws BusinessException, NotFoundException {
        try {
            billBusiness.setCancelNative(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/list/with/product/{idproduct}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> billsIdByProduct(@PathVariable("idproduct") Long idproduct) throws BusinessException, NotFoundException {
        try {
            return new ResponseEntity<>(billDetailBusiness.idBillByIdProduct(idproduct), HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(value = "")
    public ResponseEntity<?> add(@RequestBody Bill bill) {
        try {
            //TODO: esta logica no deberia estar acá, si no en capa business. salu2
            Bill auxBill = Bill.builder().broadcastDate(bill.getBroadcastDate()).expirationDate(bill.getExpirationDate()).number(bill.getNumber()).canceled(bill.isCanceled()).build();
            // Se crea una cabecera de bill
            Bill response = billBusiness.add(auxBill);
            // Se agrega todos los detalles
            billDetailBusiness.add(bill.getDetalle(), response);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("location", Constants.URL_INTEGRATION_CLI2_BILLS + "/" + response.getNumber());

            //****************** inicio auditoria ***************
            Audit audit = Audit.builder().fecha(OffsetDateTime.now()).operacion("alta").user(getUserLogged().getUsername()).bill(auxBill).build();
            auditBusiness.add(audit);
            //****************** fin auditoria ***************

            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
        } catch (FoundException e) {
            return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (BusinessException e) {
            return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "")
    public ResponseEntity<?> update(@RequestBody Bill bill) {
        try {
            Bill response = billBusiness.update(bill);
            billDetailBusiness.deleteAllByIdBill(bill.getIdBill());
            billDetailBusiness.add(bill.getDetalle(), response);

            //****************** inicio auditoria ***************
            Audit audit = Audit.builder().fecha(OffsetDateTime.now()).operacion("modificacion").user(getUserLogged().getUsername()).bill(bill).build();
            auditBusiness.add(audit);
            //****************** fin auditoria ***************

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (FoundException e) {
            throw new RuntimeException(e);
        }
    }

//    @DeleteMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws BusinessException{
//        try {
//
//            //****************** inicio auditoria ***************
//            Audit audit = Audit.builder().fecha(OffsetDateTime.now()).operacion("baja").user(getUserLogged().getUsername())
////                    .bill(bill)
//                    .build();
//            auditBusiness.add(audit);
//            //****************** fin auditoria ***************
//            billBusiness.delete(id);
//
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (NotFoundException e) {
//            return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
//        } catch (FoundException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
