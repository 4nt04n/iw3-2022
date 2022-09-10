package org.mugiwaras.backend.integration.cli2.model.persistence;

import org.mugiwaras.backend.integration.cli2.model.ProductCli2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface ProductCli2Repository extends JpaRepository<ProductCli2, Long> {

//   Optional<ProductCli2> findOneByCodCli2  (String codCli2);
    @Override
Optional<ProductCli2> findById (Long idProduct);
}
