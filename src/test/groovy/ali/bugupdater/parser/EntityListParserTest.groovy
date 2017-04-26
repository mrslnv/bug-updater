package ali.bugupdater.parser;


import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionConfiguration
import ali.bugupdater.mapping.ReqMapping
import ali.bugupdater.mapping.BugMapping

@ContextConfiguration(locations = ["classpath:AppContxt-test.xml"])
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners([DependencyInjectionTestExecutionListener.class])
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class EntityListParserTest {

  @Autowired
  EntityListParser entityListParser;

  @Autowired
  BugMapping bugMapping;

  @Autowired
  ReqMapping reqMapping;

  @Test
  public void testBug() {
    def stream = EntityListParserTest.class.getResourceAsStream("BugResponse.xml");
    entityListParser.init(bugMapping)
    entityListParser.parse(XmlSlurper.parse(new InputStreamReader(stream)), {bug -> printf "Bug: ${bug}\n"});
  }

  @Test
  public void testReq() {
    def stream = EntityListParserTest.class.getResourceAsStream("ReqResponse.xml");
    entityListParser.init(reqMapping)
    entityListParser.parse(XmlSlurper.parse(new InputStreamReader(stream)), {bug -> printf "Req: ${bug}\n"});
  }

}
