package ali.bugupdater.mapping

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ReleaseMapping extends EntityMapping {

  def mapping = [
          "id": "REL_ID",
          "parent-id": "REL_PARENT_ID",
          "has-attachments": "REL_has_attachments",
          "description": "REL_description",
          "name": "REL_name",
          "start-date": "REL_start_date",
          "end-date": "REL_end_date",
          "ver-stamp": "REL_ver_stamp"
  ];

  def table = "RELEASES"
  
  def idColumn = "REL_ID"

  String restPath = "releases"

  @Autowired
  @Value('${rest.release.query:query={}}')
  String restQuery
}
