
package com.example.evolutionerp.util;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;
@Component @RequiredArgsConstructor
public class MapperUtil {
  private final ModelMapper mapper;
  public <S,D> D map(S s, Class<D> d){ return mapper.map(s,d); }
  public <S,D> List<D> mapList(List<S> src, Class<D> d){ return src.stream().map(e->map(e,d)).collect(Collectors.toList()); }
}
