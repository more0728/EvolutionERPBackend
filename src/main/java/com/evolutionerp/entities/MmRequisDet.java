
package com.evolutionerp.entities;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
@Entity @Table(schema="evo", name="mmrequis_det") @IdClass(MmRequisDetId.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MmRequisDet {
  @Id @Column(name="cod_sociedad", length=10) private String codSociedad;
  @Id @Column(name="nro_item") private Long nroItem;
  @Id @Column(name="nro_doc", length=12) private String nroDoc;
  @Column(name="cod_material", length=42) private String codMaterial;
  @Column(name="c_unidad", nullable=false, length=10) private String cUnidad;
  @Column(nullable=false, precision=18, scale=4) private BigDecimal cantid;
  @Column(name="ncantidad_recibida", nullable=false, precision=18, scale=4) @Builder.Default private BigDecimal ncantidadRecibida=BigDecimal.ZERO;
  @Column(length=400) private String observ;
  @Column(name="ccod_proveedor", length=12) private String ccodProveedor;
  @Column(length=12) private String estado="PENDIENTE";
  @Column(name="opc_mant", length=12) private String opcMant="ACTIVO";
  @ManyToOne(fetch=FetchType.LAZY) @JoinColumns({@JoinColumn(name="cod_sociedad", referencedColumnName="cod_sociedad", insertable=false, updatable=false), @JoinColumn(name="nro_doc", referencedColumnName="nro_doc", insertable=false, updatable=false)})
  private MmRequisCab cab;
  @PrePersist @PreUpdate void upper(){ if(observ!=null) observ=observ.toUpperCase(); if(estado!=null) estado=estado.toUpperCase(); if(cUnidad!=null) cUnidad=cUnidad.toUpperCase(); }
}
