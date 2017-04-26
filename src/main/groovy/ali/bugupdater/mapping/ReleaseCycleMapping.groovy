package ali.bugupdater.mapping

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.annotation.Autowired

//@Component
class ReleaseCycleMapping extends EntityMapping {

  def mapping = [
          "id": "RCYC_ID",
          "parent-id": "RCYC_PARENT_ID",
          "has-attachments": "RCYC_has_attachments",
          "description": "RCYC_description",
          "name": "RCYC_name",
          "start-date": "RCYC_start_date",
          "end-date": "RCYC_end_date",
          "ver-stamp": "RCYC_ver_stamp"
  ];

  def table = "RELEASE_CYCLES"
  
  def idColumn = "RCYC_ID"

  def restPath = "release-cycles"

  @Autowired
  @Value('${rest.releaseCycle.query:query={}}')
  def restQuery
}
