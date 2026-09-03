
package com.evolutionerp.dtos;
import lombok.*;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MmRequisCabDTO {
  @NotBlank @Size(max=10) private String codSociedad;
  @Size(max=12) private String nroDoc;
  @NotNull private LocalDateTime fecDoc;
  @NotNull private LocalDateTime fecReq;
  @NotBlank @Size(max=10) private String ccodCencos;
  @Size(max=12) private String ccodPerson;
  @Size(max=200) private String lugarEntr;
  @Size(max=12) private String ccodProveedor;
  @Size(max=10) private String tipPrio;
  @Size(max=400) private String observ;
  @Size(max=12) private String estado;
  @Size(max=4) private String notaEntrada;
  @Size(max=1) private String condic;
  @Valid @NotEmpty private List<MmRequisDetDTO> detalles;
}
