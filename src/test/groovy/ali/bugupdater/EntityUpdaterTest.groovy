package ali.bugupdater;


import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionConfiguration
import ali.bugupdater.BugUpdater
import ali.bugupdater.ReqUpdater

@ContextConfiguration(locations = ["classpath:AppContxt-test.xml"])
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners([DependencyInjectionTestExecutionListener.class])
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class EntityUpdaterTest {

    @Autowired
    BugUpdater bugUpdater;

    @Autowired
    ReqUpdater reqUpdater;

    @Test
    public void testBug() {
      bugUpdater.init()
      bugUpdater.update()
    }

    @Test
    public void testReq() {
      reqUpdater.init()
      reqUpdater.update()
    }

}
