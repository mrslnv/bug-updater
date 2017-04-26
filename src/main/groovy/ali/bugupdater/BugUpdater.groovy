package ali.bugupdater

import ali.bugupdater.db.EntityStore
import ali.bugupdater.parser.EntityListParser

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ali.bugupdater.rest.BugGetter
import ali.bugupdater.mapping.BugMapping

@Component
class BugUpdater extends EntityUpdater{
  public static final String KEY = "B"
  
  @Autowired
  BugMapping bugMapping;

  @Autowired
  BugGetter bugGetter;

  @Autowired
  EntityListParser entityListParser;

  @Autowired
  EntityStore store;

  def BugUpdater() {
    super(KEY)
  }

  def init(){
    super.init(bugMapping,bugGetter,entityListParser,store)
  }
}
