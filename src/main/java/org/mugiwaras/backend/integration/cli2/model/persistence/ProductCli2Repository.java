package org.mugiwaras.backend.integration.cli2.model.persistence;

import org.mugiwaras.backend.integration.cli2.model.ProductCli2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCli2Repository extends JpaRepository<ProductCli2, Long> {
    @Override
    Optional<ProductCli2> findById(Long idProduct);
}
