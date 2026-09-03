
package com.evolutionerp.entities;
import jakarta.persistence.*;
import lombok.*;
@Entity @Table(schema="evo", name="bproveedor")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BProveedor {
  @Id @Column(name="ccod_proveedor", length=12) private String ccodProveedor;
  @Column(name="nom_prov", nullable=false, length=150) private String nomProv;
  @Column(length=11) private String ruc;
  @Column(length=12) private String opcMant="ACTIVO";
  @PrePersist @PreUpdate void upper(){ if(nomProv!=null) nomProv=nomProv.toUpperCase(); }
}
