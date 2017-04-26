package ali.bugupdater.db

import ali.bugupdater.common.EntityUpdateStatus
import ali.bugupdater.common.RequiresMapping
import ali.bugupdater.mapping.EntityMapping
import groovy.sql.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
class EntityStore extends RequiresMapping {
  private final Log log = LogFactory.getLog(EntityStore.class);

  String host

  @Autowired
  @Value('${jdbc.driverClassName}')
  def driverClassName

  @Autowired
  @Value('${jdbc.url}')
  def url

  @Autowired
  @Value('${jdbc.username}')
  def username
  @Autowired
  @Value('${jdbc.password}')
  def password

  @Autowired
  @Value('${jdbc.schema}')
  def schema

  Sql db;

  def EntityStore() {
  }

  def init(EntityMapping mapping) {
    super.init(mapping)
    refreshJdbc()
  }

  def refreshJdbc(){
    db = Sql.newInstance(url, username, password, driverClassName)
  }

  def doIns(bug) {
    mappingNotNull()
    def columns = [], valuesDef = [], values = []
    bug.each {key, value ->
      columns.push(key)
      valuesDef.push("?")
      values.push(entityMapping.convertValue(value))
    }
    if (!columns)
      return
    columns = columns.join(", ")
    valuesDef = valuesDef.join(", ")
    def queryStmt = "insert into ${schema}.${entityMapping.table} (${columns}) values (${valuesDef})"
    log.info (queryStmt)
    log.info (values)
    db.execute(queryStmt, values)
  }

  def doUpdate(bug) {
    def columns = [], values = []
    bug.each {key, value ->
      if (key == entityMapping.idColumn)
        return
      columns.push(key)
      values.push(entityMapping.convertValue(value))
    }
    if (!columns)
      return
    columns = columns.join("=?, ") + "=?"
    values.push(bug[entityMapping.idColumn]);
    def queryStmt = "update ${schema}.${entityMapping.table} SET ${columns} where ${entityMapping.idColumn} = ?"
    log.info (queryStmt)
    log.info (values)
    db.execute(queryStmt, values)
    if (db.updateCount == 0){
      insertFirst = false
      log.error("Cannot update object: $bug")
      throw new Exception("Cannot update object: $bug")
    }
  }

  def insertFirst = true
  def EntityUpdateStatus storeEntity(def bug) {
    try {
      try {
        return saveOrUpdate(insertFirst, bug)
      } catch (any) {             
        insertFirst = !insertFirst;
        return saveOrUpdate(insertFirst, bug)
      }
    } catch (any) {
      log.error("Error saving entity $bug",any)
    }
  }

  private def EntityUpdateStatus saveOrUpdate(boolean insertFirst, bug) {
    if (insertFirst){
      doIns(bug)
      log.debug "Insert:"+bug
      return EntityUpdateStatus.INSERT
    }else{
      doUpdate(bug)
      log.debug "Update:"+bug
      return EntityUpdateStatus.UPDATE
    }
  }

}
