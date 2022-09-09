package org.mugiwaras.backend.integration.cli2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bill_details")
//@IdClass(BillDetailKey.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillDetail implements Serializable {


    private static final long serialVersionUID = -2732161062909843295L;

    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BillDetailKey id;

    @ManyToOne
    @MapsId("idProductCli2")
    @JoinColumn(name = "id_product")
//    @JsonIgnore
    private ProductCli2 product;

    @ManyToOne
    @MapsId("idBill")
    @JoinColumn(name = "id_bill")
    @JsonIgnore
    private Bill bill;

    private Double precio;

    private Integer cantidad;


}