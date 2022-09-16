package org.mugiwaras.backend.integration.cli2.model.persistence;

import org.mugiwaras.backend.integration.cli2.model.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillDetailRepository  extends JpaRepository<BillDetail, Long> {

    @Query(value = "SELECT id_bill FROM bill_details where id_product=? ", nativeQuery = true)
    List<Long> findIdBillWithProduct(Long idProduct);
}
