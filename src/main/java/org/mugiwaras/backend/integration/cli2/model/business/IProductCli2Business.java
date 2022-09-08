package org.mugiwaras.backend.integration.cli2.model.business;

import org.mugiwaras.backend.integration.cli2.model.ProductCli2;
import org.mugiwaras.backend.model.Product;
import org.mugiwaras.backend.model.business.BusinessException;
import org.mugiwaras.backend.model.business.FoundException;
import org.mugiwaras.backend.model.business.NotFoundException;

import java.util.List;

public interface IProductCli2Business {

     ProductCli2 load(String codCli2) throws NotFoundException, BusinessException;
     List<ProductCli2> list() throws BusinessException;
     ProductCli2 add(ProductCli2 product) throws FoundException, BusinessException;

}
