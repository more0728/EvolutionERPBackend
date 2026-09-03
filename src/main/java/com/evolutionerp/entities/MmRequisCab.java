
package com.evolutionerp.entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity @Table(schema="evo", name="mmrequis_cab") @IdClass(MmRequisCabId.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MmRequisCab {
  @Id @Column(name="cod_sociedad", length=10) private String codSociedad;
  @Id @Column(name="nro_doc", length=12) private String nroDoc;
  @Column(name="fec_doc", nullable=false) private LocalDateTime fecDoc;
  @Column(name="fec_req", nullable=false) private LocalDateTime fecReq;
  @Column(name="ccod_cencos", nullable=false, length=10) private String ccodCencos;
  @Column(name="ccod_person", length=12) private String ccodPerson;
  @Column(name="lugar_entr", length=200) private String lugarEntr;
  @Column(name="ccod_proveedor", length=12) private String ccodProveedor;
  @Column(name="tip_prio", length=10) private String tipPrio;
  @Column(length=400) private String observ;
  @Column(length=12) private String estado="PENDIENTE";
  @Column(name="nota_entrada", length=4) private String notaEntrada;
  @Column(length=1) private String condic="A";
  @Column(name="opc_mant", length=12) private String opcMant="ACTIVO";
  @Column(length=4, nullable=false) private String app="COM";
  @Column(name="user_sis", nullable=false, length=12) private String userSis;
  @Column(name="user_mod", nullable=false, length=12) private String userMod;
  @Column(name="user_sis_date", nullable=false) private LocalDateTime userSisDate;
  @OneToMany(mappedBy="cab", cascade=CascadeType.ALL, orphanRemoval=true) @Builder.Default
  private List<MmRequisDet> detalles=new ArrayList<>();
  @PrePersist @PreUpdate void upper(){ if(lugarEntr!=null) lugarEntr=lugarEntr.toUpperCase(); if(observ!=null) observ=observ.toUpperCase(); if(estado!=null) estado=estado.toUpperCase(); }
}
