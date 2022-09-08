package org.mugiwaras.backend.integration.cli2.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class BillDetailKey implements Serializable {

    private static final long serialVersionUID = -6716366406218311699L;
    @Column(name = "id_bill")
    Long idBill;

    @Column(name = "id_product")
    Long idProductCli2;

}
