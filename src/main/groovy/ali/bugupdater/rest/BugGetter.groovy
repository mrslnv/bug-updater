package ali.bugupdater.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import groovy.util.slurpersupport.GPathResult

@Component
public class BugGetter extends RestGetter{

  @Autowired
  @Value('${rest.bug.urlPath}')
  String path

  @Autowired
  @Value('${rest.bug.query}')
  String query


  def BugGetter() {
  }

  def getEntities(long lastRun) {
    mappingNotNull()

    super.getEntities(path,query, lastRun)
  }

}
