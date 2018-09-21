package org.cdlib.domain.objects.bib;

import java.util.List;

public interface Identifier<T> {
  
  T getValue();
  
  List<T> getFormerValues();
  
}
