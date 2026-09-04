
package com.evolutionerp.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapperUtil {
  private final ModelMapper mapper;

  public MapperUtil(ModelMapper mapper) {
    this.mapper = mapper;
  }

  public <S, D> D map(S s, Class<D> d) {
    return mapper.map(s, d);
  }

  public <S, D> List<D> mapList(List<S> src, Class<D> d) {
    return src.stream().map(e -> map(e, d)).collect(Collectors.toList());
  }
}
