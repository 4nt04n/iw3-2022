package org.mugiwaras.backend.integration.cli2.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "bills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill implements Serializable {

    private static final long serialVersionUID = -7441929725542606602L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idBill;

    @Column(unique = true)
    private Long number;

    @Column
    private OffsetDateTime broadcastDate;

    @Column
    private OffsetDateTime expirationDate;

//    @Column(columnDefinition = "tinyint")
    @Column
    private boolean canceled;

    @OneToMany(mappedBy = "bill",fetch = FetchType.EAGER)
    List<BillDetail> detalle;


}
