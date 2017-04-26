package ali.bugupdater.mapping

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value

@Component
class ReleaseFolderMapping extends EntityMapping {

  def mapping = [
          "id": "RF_ID",
          "parent-id": "RF_PARENT_ID",
          "has-attachments": "RF_has_attachments",
          "description": "RF_description",
          "name": "RF_name",
          "path": "RF_path"
  ];

  def table = "RELEASE_FOLDERS"
  
  def idColumn = "RF_ID"

  def restPath = "release-folders"

  @Autowired
  @Value('${rest.releaseFolder.query:query={}}')
  def restQuery
}
