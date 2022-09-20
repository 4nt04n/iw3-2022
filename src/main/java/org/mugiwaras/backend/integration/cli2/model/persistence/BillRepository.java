package org.mugiwaras.backend.integration.cli2.model.persistence;

import org.mugiwaras.backend.integration.cli2.model.Bill;
import org.mugiwaras.backend.integration.cli2.model.IBillViewV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    @Query(value = "SELECT * FROM bills WHERE number=?", nativeQuery = true)
    Optional<Bill> findOneByNumberV1(Long number);//v1 y v0

    Optional<IBillViewV2> findOneByNumber(Long number);//v2

    @Query(value = "    select * from bills", nativeQuery = true)
    List<IBillViewV2> findAllV2();//v2 list


    @Transactional
    @Modifying
    @Query(value = "UPDATE bills SET canceled=1 WHERE id_bill=?", nativeQuery = true)
    void updateCancelById(Long id);

}
