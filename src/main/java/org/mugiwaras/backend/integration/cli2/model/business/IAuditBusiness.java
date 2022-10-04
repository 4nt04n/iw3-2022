package org.mugiwaras.backend.integration.cli2.model.business;

import org.mugiwaras.backend.integration.cli2.model.Audit;
import org.mugiwaras.backend.model.business.BusinessException;
import org.mugiwaras.backend.model.business.FoundException;

import java.util.List;

public interface IAuditBusiness {

    List<Audit> list(String user) throws BusinessException;

    Audit add(Audit audit) throws FoundException, BusinessException;
}
