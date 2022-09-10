package org.mugiwaras.backend.integration.cli2.model.persistence;

import org.mugiwaras.backend.integration.cli2.model.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDetailRepository  extends JpaRepository<BillDetail, Long> {

}
