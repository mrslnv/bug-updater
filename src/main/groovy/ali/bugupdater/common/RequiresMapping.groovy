package ali.bugupdater.common

import ali.bugupdater.mapping.EntityMapping

class RequiresMapping {

  EntityMapping entityMapping;

  def init(EntityMapping mapping) {
    this.entityMapping = mapping
  }

  def mappingNotNull(){
    if (!entityMapping)
      throw new Exception("Run init and supply mapping")
  }
}
