package ali.bugupdater

import ali.bugupdater.common.RequiresMapping
import ali.bugupdater.common.UpdateStatus
import ali.bugupdater.db.EntityStore
import ali.bugupdater.mapping.EntityMapping
import ali.bugupdater.parser.EntityListParser
import ali.bugupdater.rest.RestGetter
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

abstract class EntityUpdater extends RequiresMapping {
  private final Log log = LogFactory.getLog(EntityUpdater.class);

  RestGetter restGetter

  EntityListParser entityListParser;

  EntityStore store;

  String updaterKey

  def private EntityUpdater(){
  }

  def protected EntityUpdater(String updaterKey){
    this.updaterKey = updaterKey
  }

  def init(EntityMapping mapping, RestGetter restGetter, EntityListParser entityListParser, EntityStore store) {
    super.init(mapping)
    this.restGetter = restGetter
    this.entityListParser = entityListParser
    this.store = store
    restGetter.init(mapping)
    entityListParser.init(mapping)
    store.init(mapping)
  }

  def String updaterKey(){
    updaterKey
  }

  def UpdateStatus update(long lastRun) {
    log.info("Update started ($updaterKey); Previous last run: "+new Date(lastRun).format("yyyy-MM-dd HH:mm:ss"))
    mappingNotNull()
    def status = new UpdateStatus(lastRun)
    def bugPages = restGetter.getEntities(lastRun)
    log.info("Pages: ${bugPages.size()}")
    store.refreshJdbc()
    bugPages.each {
      entityListParser.parse(it, {bug ->
        def es = store.storeEntity(bug)
        status.add(es)
      })
    }
    log.info "Update Finished: $status"
    status
  }
}
