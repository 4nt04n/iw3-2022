package org.mugiwaras.backend.integration.cli2.model.business;

import org.mugiwaras.backend.integration.cli2.model.Bill;
import org.mugiwaras.backend.integration.cli2.model.BillDetail;
import org.mugiwaras.backend.model.Product;
import org.mugiwaras.backend.model.business.BusinessException;
import org.mugiwaras.backend.model.business.FoundException;
import org.mugiwaras.backend.model.business.NotFoundException;

import java.util.List;

public interface IBillBusiness {

    public Bill load(long id) throws NotFoundException, BusinessException;

    public Bill loadByNumber(Long number) throws NotFoundException, BusinessException; // numero de factura

    public List<Bill> list() throws BusinessException;

    public List<Bill> listNoNull() throws BusinessException;

    public Bill add(Bill bill) throws FoundException, BusinessException;

    public Bill update(Bill bill) throws NotFoundException, BusinessException;

    public Bill disable(long number) throws NotFoundException, BusinessException;

    public Bill setCancel(Bill bill) throws NotFoundException, BusinessException;

    public void delete(long id) throws NotFoundException, BusinessException;

}
