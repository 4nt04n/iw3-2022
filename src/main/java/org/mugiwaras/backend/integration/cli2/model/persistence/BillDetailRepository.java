package org.mugiwaras.backend.integration.cli2.model.persistence;

import org.mugiwaras.backend.integration.cli2.model.BillDetail;
import org.mugiwaras.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillDetailRepository  extends JpaRepository<BillDetail, Long> {
    List<BillDetail> findAllById_IdBill(long idBill);
}

