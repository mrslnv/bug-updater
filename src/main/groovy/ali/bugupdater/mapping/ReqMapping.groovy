package ali.bugupdater.mapping

import org.springframework.stereotype.Component

@Component
class ReqMapping extends EntityMapping{

  def mapping = [
          "id": "RQ_REQ_ID",
          "dev-comments": "RQ_REQ_COMMENT",
          "status": "RQ_REQ_STATUS",
          "req-type": "RQ_REQ_TYPE",
          "parent-id": "RQ_FATHER_ID",
          "owner": "RQ_REQ_AUTHOR",
          "name": "RQ_REQ_NAME",
          "creation-time": "RQ_REQ_DATE",
          "type-id": "RQ_TYPE_ID",
          "hierarchical-path": "RQ_REQ_PATH",
          "last-modified": "RQ_VTS",

          "has-rich-content": "RQ_HAS_RICH_CONTENT",
          "istemplate": "RQ_ISTEMPLATE",
          "no-of-sons": "RQ_NO_OF_SONS",
          "order-id": "RQ_ORDER_ID",
          "req-rich-content": "RQ_REQ_RICH_CONTENT",
          "req-ver-stamp": "RQ_REQ_VER_STAMP",
          "type-id": "RQ_TYPE_ID",
          "target-rel": "RQ_TARGET_REL",
          "target-rcyc": "RQ_TARGET_RCYC",
          "req-priority":"RQ_REQ_PRIORITY"
  ];

  def table = "REQ"

  def idColumn = "RQ_REQ_ID"
}
