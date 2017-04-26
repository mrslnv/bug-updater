package ali.bugupdater.rest;


import ali.bugupdater.common.RequiresMapping
import ali.bugupdater.mapping.EntityMapping
import groovy.util.slurpersupport.GPathResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value

public abstract class AbstractGetter extends RequiresMapping {
  @Autowired
  @Value('${rest.host}')
  String host
  //"mydtqc01.isr.hp.com"

  @Autowired
  @Value('${rest.port}')
  String port
  //"8080"

  @Autowired
  @Value('${rest.username}')
  String userName

  @Autowired
  @Value('${rest.password}')
  String password

  @Autowired
  @Value('${rest.pageSize}')
  String pageSize

  def init(EntityMapping mapping) {
    super.init(mapping)
  }

  public abstract getEntities(long lastRun) throws Exception;

  protected abstract getEntities(String path, String query, long lastRun) throws Exception;
}