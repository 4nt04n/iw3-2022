package org.mugiwaras.backend.integration.cli2.model.persistence;

import org.mugiwaras.backend.integration.cli2.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
//    Optional<Bill> findOneByIdBill (Long idBill);
//    Optional<Bill> findAllCanceledTrue();
    Optional<Bill> findOneByNumber(Long number);
}