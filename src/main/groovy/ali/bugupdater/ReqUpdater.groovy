package ali.bugupdater

import ali.bugupdater.db.EntityStore
import ali.bugupdater.parser.EntityListParser
import ali.bugupdater.rest.ReqGetter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.context.annotation.Scope
import org.springframework.beans.factory.config.BeanDefinition
import ali.bugupdater.mapping.ReqMapping

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
class ReqUpdater extends EntityUpdater{
  public static final String KEY = "R"

  @Autowired
  ReqMapping reqMapping;

  @Autowired
  ReqGetter reqGetter;

  @Autowired
  EntityListParser entityListParser;

  @Autowired
  EntityStore store;

  def ReqUpdater() {
    super(KEY)
  }


  def init(){
    super.init(reqMapping,reqGetter,entityListParser,store)
  }
}
