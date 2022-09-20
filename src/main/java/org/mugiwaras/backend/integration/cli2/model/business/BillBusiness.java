package org.mugiwaras.backend.integration.cli2.model.business;

import lombok.extern.slf4j.Slf4j;
import org.mugiwaras.backend.integration.cli2.model.Bill;
import org.mugiwaras.backend.integration.cli2.model.IBillViewV2;
import org.mugiwaras.backend.integration.cli2.model.persistence.BillDetailRepository;
import org.mugiwaras.backend.integration.cli2.model.persistence.BillRepository;
import org.mugiwaras.backend.model.business.BusinessException;
import org.mugiwaras.backend.model.business.FoundException;
import org.mugiwaras.backend.model.business.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BillBusiness implements IBillBusiness {

    @Autowired(required = false)
    private BillRepository billRepository;

    @Autowired(required = false)
    private BillDetailRepository billDetailRepository;

//    @Autowired(required = false)
//    private BillViewRepository billViewRepository;

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
            throw NotFoundException.builder().message("No se encuentra la factura id=" + id).build();
        }

        return r.get();
    }


    @Override
    public Bill loadByNumberV1(Long number) throws NotFoundException, BusinessException {
        Optional<Bill> r;
        try {
            r = billRepository.findOneByNumberV1(number);
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
    public IBillViewV2 loadByNumberV2(Long number) throws NotFoundException, BusinessException {
        Optional<IBillViewV2> r;
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
    public List<IBillViewV2> listV2() throws BusinessException {
        try {
            return billRepository.findAllV2();
//            return billViewRepository.findAll();
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

    public void setCancelNative(Long id) throws NotFoundException, BusinessException {
        load(id);
        try {
            billRepository.updateCancelById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
            throw FoundException.builder().message("Se encontró la factura numero'" + bill.getNumber() + "'").build();
        } catch (NotFoundException e) {
        }

        try {
            return billRepository.save(bill);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw BusinessException.builder().ex(e).build();
        }

    }


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

    @Override
    public void delete(long id) throws NotFoundException, BusinessException {
        this.load(id);
        try {
            billRepository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw BusinessException.builder().ex(e).build();
        }


    }
}
