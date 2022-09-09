package org.mugiwaras.backend.integration.cli2.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
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
    private OffsetDateTime broadcastDate;

    @Column
    private OffsetDateTime expirationDate;

    @Column(columnDefinition = "tinyint default 0")
    private boolean canceled = false;

//    @OneToMany(mappedBy = "bill")
    @OneToMany(mappedBy = "bill", fetch = FetchType.EAGER)
//            @JoinTable
    List<BillDetail> detalle;


}
