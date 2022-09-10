package org.mugiwaras.backend.integration.cli2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mugiwaras.backend.model.Product;

import javax.persistence.*;

@Entity
@Table(name="cli2_products")
@PrimaryKeyJoinColumn(name = "id_product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductCli2 extends Product {

    private static final long serialVersionUID = -3227781224998922643L;

    @Column(nullable = false)
    private String codCli2;

}