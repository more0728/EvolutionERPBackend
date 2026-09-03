
package com.evolutionerp.model;
import jakarta.persistence.*;
import lombok.*;
@Entity @Table(schema="evo", name="epersonal")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EPersonal {
  @Id @Column(name="ccod_person", length=12) private String ccodPerson;
  @Column(name="cod_sociedad", length=10) private String codSociedad;
  @Column(name="nom_person", nullable=false, length=100) private String nomPerson;
  @Column(length=12) private String opcMant="ACTIVO";
  @PrePersist @PreUpdate void upper(){ if(nomPerson!=null) nomPerson=nomPerson.toUpperCase(); }
}
