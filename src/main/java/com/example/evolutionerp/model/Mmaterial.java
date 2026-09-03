
package com.example.evolutionerp.model;
import jakarta.persistence.*;
import lombok.*;
@Entity @Table(schema="evo", name="mmaterial")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Mmaterial {
  @Id @Column(name="cod_material", length=42) private String codMaterial;
  @Column(name="nom_material", nullable=false, length=200) private String nomMaterial;
  @Column(name="c_unidad", nullable=false, length=10) private String cUnidad;
  @Column(length=12) private String opcMant="ACTIVO";
  @PrePersist @PreUpdate void upper(){ if(nomMaterial!=null) nomMaterial=nomMaterial.toUpperCase(); if(cUnidad!=null) cUnidad=cUnidad.toUpperCase(); }
}
