package ali.bugupdater;


import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionConfiguration

@ContextConfiguration(locations = ["classpath:AppContxt-test.xml"])
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners([DependencyInjectionTestExecutionListener.class])
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class RunnerTest {

    @Autowired
    Runner runner;

    @Test
    public void testAutowireOfEntityUpdaters() {
      def size = runner.entityUpdaters.size()
      printf "Autowired updaters: $size"
    }


  @Test
  public void testPeriodicRuns() {
    runner.stop()
    def dumRunner = new Runner()
    dumRunner.scheduler = runner.scheduler
    def i = [];
    i[0] = 0
    def dumdater = new EntityUpdater(){
      def update() {
        println "Run: ${i[0]}"
        i[0]++
      }
      def init(){
      }
    }
    dumRunner.entityUpdaters = [dumdater]
    dumRunner.period(100)
    dumRunner.init()
    sleep(5000)
    assert i[0]>0
  }

  @Test
  public void testRun() {
    runner.period 100
    sleep(200)
    runner.stop()
    sleep(100000)
    System.exit(0)
  }
}
