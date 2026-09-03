
package com.evolutionerp.model;
import jakarta.persistence.*;
import lombok.*;
@Entity @Table(schema="evo", name="eccosto") @IdClass(EcCostoId.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EcCosto {
  @Id @Column(name="cod_sociedad", length=10) private String codSociedad;
  @Id @Column(name="ccod_cencos", length=10) private String ccodCencos;
  @Column(name="nom_cencos", nullable=false, length=100) private String nomCencos;
  @Column(length=12) private String opcMant="ACTIVO";
  @PrePersist @PreUpdate void upper(){ if(nomCencos!=null) nomCencos=nomCencos.toUpperCase(); }
}
