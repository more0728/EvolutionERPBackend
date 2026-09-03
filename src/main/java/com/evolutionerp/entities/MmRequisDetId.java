
package com.evolutionerp.entities;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MmRequisDetId implements Serializable {
    private String codSociedad;
    private String nroDoc;
    private Long nroItem;
}
