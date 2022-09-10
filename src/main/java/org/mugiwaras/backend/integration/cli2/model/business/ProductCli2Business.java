package org.mugiwaras.backend.integration.cli2.model.business;

import lombok.extern.slf4j.Slf4j;
import org.mugiwaras.backend.integration.cli2.model.ProductCli2;
import org.mugiwaras.backend.integration.cli2.model.persistence.ProductCli2Repository;
import org.mugiwaras.backend.model.business.BusinessException;
import org.mugiwaras.backend.model.business.FoundException;
import org.mugiwaras.backend.model.business.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductCli2Business implements IProductCli2Business {

    @Autowired
    private ProductCli2Repository productCli2DAO;

    @Override
    public ProductCli2 load(Long idProduct) throws NotFoundException, BusinessException {
        Optional<ProductCli2> r;
        try {
            r = productCli2DAO.findById(idProduct);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw BusinessException.builder().ex(e).build();
        }
        if (r.isEmpty()) {
            throw NotFoundException.builder().message("No se encuentra el Producto id=" + idProduct).build();
        }

        return r.get();
    }

    @Override
    public List<ProductCli2> list() throws BusinessException {
        try {
            return productCli2DAO.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw BusinessException.builder().ex(e).build();
        }
    }

    @Override
    public ProductCli2 add(ProductCli2 product) throws FoundException, BusinessException {
        try {
            load(product.getId());
            throw FoundException.builder().message("Se encontró el Producto id=" + product.getId()).build();
        } catch (NotFoundException e) {
        }
        try {
            return productCli2DAO.save(product);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw BusinessException.builder().ex(e).build();
        }

    }
}
