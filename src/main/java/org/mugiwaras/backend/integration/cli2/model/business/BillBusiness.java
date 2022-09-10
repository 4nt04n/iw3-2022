package org.mugiwaras.backend.integration.cli2.model.business;

import lombok.extern.slf4j.Slf4j;
import org.mugiwaras.backend.integration.cli2.model.Bill;
import org.mugiwaras.backend.integration.cli2.model.BillDetail;
import org.mugiwaras.backend.integration.cli2.model.persistence.BillDetailRepository;
import org.mugiwaras.backend.integration.cli2.model.persistence.BillRepository;
import org.mugiwaras.backend.model.Product;
import org.mugiwaras.backend.model.business.BusinessException;
import org.mugiwaras.backend.model.business.FoundException;
import org.mugiwaras.backend.model.business.NotFoundException;
import org.mugiwaras.backend.model.persistence.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BillBusiness implements IBillBusiness {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillDetailRepository billDetailRepository;

    @Override
    public Bill load(long id) throws NotFoundException, BusinessException {
        Optional<Bill> r;
        try {
            r = billRepository.findById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw BusinessException.builder().ex(e).build();
        }
        if (r.isEmpty()) {
            throw NotFoundException.builder().message("No se encuentra el Producto id=" + id).build();
        }

        return r.get();
    }


    @Override
    public Bill loadByNumber(Long number) throws NotFoundException, BusinessException {
        Optional<Bill> r;
        try {
            r = billRepository.findOneByNumber(number);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw BusinessException.builder().ex(e).build();
        }
        if (r.isEmpty()) {
            throw NotFoundException.builder().message("No se encuentra la factura numero '" + number + "'").build();
        }
        return r.get();
    }

    @Override
    public List<Bill> list() throws BusinessException {
        try {
            return billRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw BusinessException.builder().ex(e).build();
        }

    }

    @Override
    public List<Bill> listNoNull() throws BusinessException {
        try {
            List<Bill> billList = new ArrayList<>();
            for (Bill item : this.list()) {
                if (!item.isCanceled()) {
                    billList.add(item);
                }
            }

            return billList;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw BusinessException.builder().ex(e).build();
        }
    }

    public Bill setCancel(Bill bill) throws BusinessException, NotFoundException {
        try {
            bill.setCanceled(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return update(bill);
    }

    @Override
    public Bill add(Bill bill) throws FoundException, BusinessException {
        try {
            load(bill.getIdBill());
            throw FoundException.builder().message("Se encontró la factura con id=" + bill.getIdBill()).build();
        } catch (NotFoundException e) {
        }
        try {
            load(bill.getNumber());
            throw FoundException.builder().message("Se encontró la factura '" + bill.getNumber() + "'").build();
        } catch (NotFoundException e) {
        }

        try {
            return billRepository.save(bill);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw BusinessException.builder().ex(e).build();
        }

    }

//    @Override
//    public BillDetail addDetail(BillDetail billDetail) throws BusinessException, NotFoundException {
//        try {
//            load(billDetail.getBill().getIdBill());
//        } catch (NotFoundException e) {
//            throw NotFoundException.builder().ex(e).build();
//        }
//        try {
//            return billDetailRepository.save(billDetail);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            throw BusinessException.builder().ex(e).build();
//        }
//
//    }

    @Override
    public Bill update(Bill bill) throws NotFoundException, BusinessException {
        load(bill.getIdBill());
        try {
            return billRepository.save(bill);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw BusinessException.builder().ex(e).build();
        }
    }

    @Override
    public Bill disable(long number) throws NotFoundException, BusinessException {
        return null;
    }

    public void delete(long id) throws NotFoundException, BusinessException {
        load(id);
        try {
            billRepository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw BusinessException.builder().ex(e).build();
        }


    }
}
