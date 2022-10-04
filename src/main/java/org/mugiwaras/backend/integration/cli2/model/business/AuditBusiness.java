package org.mugiwaras.backend.integration.cli2.model.business;

import lombok.extern.slf4j.Slf4j;
import org.mugiwaras.backend.integration.cli2.model.Audit;
import org.mugiwaras.backend.integration.cli2.model.persistence.AuditRepository;
import org.mugiwaras.backend.model.business.BusinessException;
import org.mugiwaras.backend.model.business.FoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AuditBusiness implements IAuditBusiness {

    @Autowired(required = false)
    private AuditRepository auditRepository;

    @Override
    public List<Audit> list(String user) throws BusinessException {
        try {
            return auditRepository.findAllByUser(user);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw BusinessException.builder().ex(e).build();
        }

    }

    @Override
    public Audit add(Audit audit) throws FoundException, BusinessException {
        try {
            return auditRepository.save(audit);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw BusinessException.builder().ex(e).build();
        }
    }
}
