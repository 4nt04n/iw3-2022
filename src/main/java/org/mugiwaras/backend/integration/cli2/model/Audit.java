package org.mugiwaras.backend.integration.cli2.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Table(name = "audit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Audit implements Serializable {
    private static final long serialVersionUID = -6412285288639757349L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private OffsetDateTime fecha;

    @Column
    private String operacion;

    @Column
    private String user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_bill")
    private Bill bill;
}
