package ali.bugupdater.parser

import ali.bugupdater.common.RequiresMapping
import org.springframework.stereotype.Component
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.Scope
import groovy.util.slurpersupport.GPathResult

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
class EntityListParser extends RequiresMapping {

  // takes map of values, mapping -- returns --> Bug
  def createBug = {xmlValues, mapping ->
    def ret = new HashMap<String,String>();
    mapping.each {key, value ->
      ret[value] = xmlValues[key]
    }
    ret
  }

  // processing {bug->do what ever}
  void parse(GPathResult input, Closure bugProcessing) {
    mappingNotNull()

    def entities = input.Entity;

    Map<String, String> xmlValues = new HashMap<String, String>((int)entities.size())
    //for each bug entry
    entities.each {
      entity ->
      // iterate through Fields and create Map [ @Name -> Value]
      entity.Fields.Field.each {field -> xmlValues[field.@Name.toString()] = field.Value.toString()}
      def bug = this.createBug(xmlValues, this.entityMapping.mapping)
      xmlValues.clear()
      bugProcessing.call(bug)
    }
  }

}
