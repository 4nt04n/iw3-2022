package org.mugiwaras.backend.integration.cli2.model;

import java.time.OffsetDateTime;
import java.util.List;

public interface IBillViewV2 {

    List<BillDetail> getDetalle();
    interface billDetail {

        BillDetailKey getId();

        ProductCli2 getProduct();

        Double getPrecio();

        Integer getCantidad();
    }




}
