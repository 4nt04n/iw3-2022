package org.mugiwaras.backend.integration.cli2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillDetailKey implements Serializable {

    private static final long serialVersionUID = -6716366406218311699L;
    @Column(name = "id_bill")
    Long idBill;

    @Column(name = "id_product")
    Long idProductCli2;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        BillDetailKey that = (BillDetailKey) o;
        return Objects.equals(idBill, that.idBill) &&
                Objects.equals(idProductCli2, that.idProductCli2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBill, idProductCli2);
    }

}
