package org.mugiwaras.backend.integration.cli2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "bills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bill implements Serializable {

    private static final long serialVersionUID = -7441929725542606602L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idBill;

    @Column(unique = true)
    private Long number;

    @Column
    private Date broadcastDate;

    @Column
    private Date expirationDate;

    @Column(columnDefinition = "tinyint default 0")
    private boolean canceled = false;

    @OneToMany(mappedBy = "bill")
    Set<BillDetail> detalle;



}
