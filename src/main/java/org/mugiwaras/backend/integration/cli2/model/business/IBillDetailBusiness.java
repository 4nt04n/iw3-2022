package org.mugiwaras.backend.integration.cli2.model.business;
import org.mugiwaras.backend.integration.cli2.model.Bill;
import org.mugiwaras.backend.integration.cli2.model.BillDetail;
import org.mugiwaras.backend.model.business.BusinessException;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface IBillDetailBusiness {

    public Optional<BillDetail> load(long id);
    @Nullable
    public List<BillDetail> list();
    void add(BillDetail billDetail, Long idBill, Long idProduct);

    public List<Long> idBillByIdProduct(Long idProduct) throws BusinessException;

    void add(List<BillDetail> detalles, Bill bill);

    public void deleteAllByIdBill(long idBill) throws BusinessException;

}