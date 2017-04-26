package ali.bugupdater.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import groovy.util.slurpersupport.GPathResult

@Component
class ReqGetter extends RestGetter{
  @Autowired
  @Value('${rest.requirement.urlPath}')
  String path
  
  @Autowired
  @Value('${rest.requirement.query}')
  String query


  def getEntities(long lastRun) {
    mappingNotNull()
    
    super.getEntities(path,query, lastRun)
  }

}
