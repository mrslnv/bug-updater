package ali.bugupdater.mapping

import org.springframework.stereotype.Component
import ali.bugupdater.mapping.EntityMapping

@Component
class BugMapping extends EntityMapping {

  def mapping = [
          "id": "BG_BUG_ID",
          "status": "BG_STATUS",
          "severity": "BG_SEVERITY",
          "priority": "BG_PRIORITY",
          "owner": "BG_RESPONSIBLE",
          "creation-time": "BG_DETECTION_DATE",
          "closing-date": "BG_CLOSING_DATE",
          "description": "BG_DESCRIPTION",
          "name": "BG_SUMMARY",
          "dev-comments": "BG_DEV_COMMENTS",
          "to-mail": "BG_TO_MAIL",
          "cycle-id": "BG_CYCLE_ID",
          "detected-in-rel": "BG_DETECTED_IN_REL",
          "project": "BG_PROJECT",
          "target-rel": "BG_TARGET_REL",
          "detected-in-rcyc": "BG_DETECTED_IN_RCYC",
          "planned-closing-ver": "BG_PLANNED_CLOSING_VER",
          "reproducible": "BG_REPRODUCIBLE",
          "has-change": "BG_HAS_CHANGE",
          "last-modified": "BG_VTS"
  ];

  def table = "BUG"
  
  def idColumn = "BG_BUG_ID"
}
