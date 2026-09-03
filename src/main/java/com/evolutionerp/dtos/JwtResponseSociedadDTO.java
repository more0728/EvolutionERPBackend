
package com.evolutionerp.dtos;
import lombok.*;
import java.util.List;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class JwtResponseSociedadDTO { private String token; private String username; private List<String> sociedades; private String sociedadActual; }
