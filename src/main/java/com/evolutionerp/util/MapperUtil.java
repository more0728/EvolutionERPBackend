
package com.evolutionerp.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class MapperUtil {
  private final ModelMapper modelMapper;

  public MapperUtil(ModelMapper modelMapper) {
    this.modelMapper = Objects.requireNonNull(modelMapper, "modelMapper no debe ser null");
  }

  /**
   * Convierte un objeto origen al tipo destino.
   *
   * @param source objeto a convertir; si es null retorna null
   * @param destinationType clase destino, no null
   * @return objeto convertido
   */
  public <Source, Destination> Destination map(Source source, Class<Destination> destinationType) {
    Objects.requireNonNull(destinationType, "destinationType no debe ser null");
    if (source == null) {
      return null;
    }
    return modelMapper.map(source, destinationType);
  }

  /**
   * Convierte una lista de objetos al tipo destino.
   *
   * @param sourceList lista a convertir; si es null o vacia retorna lista vacia
   * @param destinationType clase destino, no null
   * @return lista convertida, nunca null
   */
  public <Source, Destination> List<Destination> mapList(List<Source> sourceList,
      Class<Destination> destinationType) {
    Objects.requireNonNull(destinationType, "destinationType no debe ser null");
    if (sourceList == null || sourceList.isEmpty()) {
      return Collections.emptyList();
    }
    return sourceList.stream().map(element -> map(element, destinationType)).toList();
  }
}
