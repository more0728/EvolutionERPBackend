
package com.example.evolutionerp.dtos;
import lombok.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MmRequisDetDTO {
  private Long nroItem;
  @Size(max=42) private String codMaterial;
  @NotBlank @Size(max=10) private String cUnidad;
  @NotNull @DecimalMin(value="0.0", inclusive=true) private BigDecimal cantid;
  private BigDecimal ncantidadRecibida;
  @Size(max=400) private String observ;
  @Size(max=12) private String ccodProveedor;
  @Size(max=12) private String estado;
}
