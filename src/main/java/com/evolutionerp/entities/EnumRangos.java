
package com.evolutionerp.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(schema = "evo", name = "enumrangos")
@IdClass(EnumRangosId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnumRangos {
  @Id
  @Column(name = "cod_sociedad", length = 10)
  private String codSociedad;
  @Id
  @Column(length = 4)
  private String app;
  @Column(name = "ultimo_num", nullable = false)
  private Integer ultimoNum;
  @Column(name = "nro_doc", insertable = false, updatable = false, length = 12)
  private String nroDoc;
}
