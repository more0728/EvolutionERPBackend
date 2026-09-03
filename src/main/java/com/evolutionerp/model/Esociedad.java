
package com.evolutionerp.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity @Table(schema="evo", name="esociedad")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Esociedad {
  @Id @Column(name="cod_sociedad", length=10) private String codSociedad;
  @Column(name="nom_sociedad", nullable=false, length=100) private String nomSociedad;
  @Column(name="nit_sociedad", nullable=false, length=11) private String nitSociedad;
  @Column(length=2) private String idPais="PE";
  @Column(length=2) private String idIdioma="ES";
  @Column(name="nom_comercial", length=100) private String nomComercial;
  @Column(length=12) private String opcMant="ACTIVO";
  @Column(name="user_sis", nullable=false, length=12) private String userSis;
  @Column(name="user_mod", nullable=false, length=12) private String userMod;
  @Column(name="user_sis_date", nullable=false) private LocalDateTime userSisDate;
  @PrePersist @PreUpdate void upper(){ if(nomSociedad!=null) nomSociedad=nomSociedad.toUpperCase(); if(nomComercial!=null) nomComercial=nomComercial.toUpperCase(); }
}
