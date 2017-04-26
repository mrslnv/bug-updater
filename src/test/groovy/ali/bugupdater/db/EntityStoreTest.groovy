package ali.bugupdater.db;


import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionConfiguration
import ali.bugupdater.mapping.BugMapping
import ali.bugupdater.mapping.ReqMapping

@ContextConfiguration(locations = ["classpath:AppContxt-test.xml"])
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners([DependencyInjectionTestExecutionListener.class])
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class EntityStoreTest {

    @Autowired
    EntityStore bugStore;

    @Autowired
    EntityStore reqStore;

    @Autowired
    BugMapping bugMapping;

    @Autowired
    ReqMapping reqMapping;

    @Test
    public void testBugs() {
      bugStore.init(bugMapping)
      def bug = ["BG_BUG_ID": "123123", "BG_SEVERITY": "1-Urgent", "BG_CLOSING_DATE": "2011-01-02"]
      bugStore.storeEntity(bug)
      bug["BG_SEVERITY"] = "2-Very High"
      bugStore.storeEntity(bug)
    }

    @Test
    public void testReqs() {
      reqStore.init(reqMapping)
      def req = ["RQ_REQ_ID": "123123", "RQ_REQ_COMMENT": "Comment", "RQ_REQ_AUTHOR": "Franta Vocas", "RQ_REQ_PATH": "AABBCC","RQ_TYPE_ID":"1"]
      reqStore.storeEntity(req)
      req["RQ_REQ_AUTHOR"] = "Updated"
      reqStore.storeEntity(req)
    }

}
