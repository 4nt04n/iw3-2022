package org.mugiwaras.backend.integration.cli2.controllers;
import org.mugiwaras.backend.controllers.BaseRestController;
import org.mugiwaras.backend.controllers.Constants;
import org.mugiwaras.backend.integration.cli2.model.Bill;
import org.mugiwaras.backend.integration.cli2.model.BillDetail;
import org.mugiwaras.backend.integration.cli2.model.BillDetailKey;
import org.mugiwaras.backend.integration.cli2.model.business.IBillBusiness;
import org.mugiwaras.backend.integration.cli2.model.business.IBillDetailBusiness;
import org.mugiwaras.backend.model.business.BusinessException;
import org.mugiwaras.backend.model.business.FoundException;
import org.mugiwaras.backend.model.business.NotFoundException;
import org.mugiwaras.backend.util.IStandartResponseBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.URL_INTEGRATION_CLI2_BILLS)
public class BillRestController extends BaseRestController {
    @Autowired
    private IStandartResponseBusiness response;

    @Autowired
    private IBillBusiness billBusiness;

    @Autowired()
    private IBillDetailBusiness billDetailBusiness;


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list() {
        try {

            return new ResponseEntity<>(billBusiness.list(), HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/no/anuladas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listNoNull() {
        try {
            return new ResponseEntity<>(billBusiness.listNoNull(), HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loadByCode(@PathVariable("number") Long number) {
        try {
            return new ResponseEntity<>(billBusiness.loadByNumber(number), HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "")
    public ResponseEntity<?> add(@RequestBody Bill bill) {
        try {

           Bill auxBill= new Bill();
           auxBill.setBroadcastDate(bill.getBroadcastDate());
           auxBill.setExpirationDate(bill.getExpirationDate());
           auxBill.setNumber(bill.getNumber());
           auxBill.setCanceled(bill.isCanceled());
            Bill response = billBusiness.add(auxBill);
            for (BillDetail item: bill.getDetalle()
                 ) {
                item.setBill(response);
            billDetailBusiness.add(item,response.getIdBill(),item.getProduct().getId());
            }

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("location", Constants.URL_INTEGRATION_CLI2_BILLS + "/" + response.getIdBill());
            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
        } catch (FoundException e) {
            return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (BusinessException e) {
            return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
