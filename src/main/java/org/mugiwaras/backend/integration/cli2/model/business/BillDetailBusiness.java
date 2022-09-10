package org.mugiwaras.backend.integration.cli2.model.business;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.mugiwaras.backend.integration.cli2.model.BillDetail;
import org.mugiwaras.backend.integration.cli2.model.BillDetailKey;
import org.mugiwaras.backend.integration.cli2.model.persistence.BillDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BillDetailBusiness implements IBillDetailBusiness {

    @Autowired(required = false)
    private BillDetailRepository billDetailRepository;

    @Override
    public Optional<BillDetail> load(long id) {
        Optional<BillDetail> r = billDetailRepository.findById(1L);
        return r;
    }

    @Override
    @SneakyThrows
    public List<BillDetail> list() {
//        if(billDetailRepository.findById(1L).isEmpty()){
//            return null;
//        }
        List<BillDetail> r = billDetailRepository.findAll();
        return r;
    }

    @Override
    public void add(BillDetail billDetail, Long idBill, Long idProduct) {
        BillDetailKey key = new BillDetailKey();
        key.setIdBill(idBill);
        key.setIdProductCli2(idProduct);
        billDetail.setId(key);
        billDetailRepository.save(billDetail);
    }


}