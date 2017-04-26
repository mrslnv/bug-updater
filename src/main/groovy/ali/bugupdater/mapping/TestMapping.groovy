package ali.bugupdater.mapping

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class TestMapping extends EntityMapping {

  def mapping = [
          "estimate-devtime": "todoBUG_ID",
          "vc-start-audit-action-id": "todoSTATUS",
          "description": "todoSEVERITY",
          "alert-data": "todoPRIORITY",
          "vc-version-number": "todoRESPONSIBLE",
          "dev-comments": "todoDETECTION_DATE",
          "test-exec-time": "todoCLOSING_DATE",
          "template": "todo",
          "status": "todo",
          "todo": "todo",
  ];

  def table = "todo"
  
  def idColumn = "todo"

  def restPath = "tests"

  @Autowired
  @Value('${rest.test.query:query={}}')
  def restQuery
}
