package ali.bugupdater

import ali.bugupdater.common.UpdateStatus
import ali.bugupdater.db.RunnerStore
import java.util.concurrent.ScheduledFuture
import javax.annotation.PostConstruct
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Component

@Component
class Runner {
  private final Log log = LogFactory.getLog(Runner.class);

  @Autowired
  Collection<EntityUpdater> entityUpdaters

  @Autowired
  RunnerStore store

  @Autowired
  @Value('${runner.period}')
  int period

  @Autowired
  TaskScheduler scheduler

  boolean run = true

  ScheduledFuture schedule

  @PostConstruct
  def void init(){
    if (!run)
      return
    entityUpdaters.each({it.init()})
  }

  private ScheduledFuture scheduleUpdate() {
    if (schedule != null)
        schedule.cancel(false)
    log.info ("Staring "+new Date()+" updaters:"+entityUpdaters.size()+ " period:"+period)
    schedule = scheduler.scheduleAtFixedRate(new Runnable() {
      void run() {
        if (!run)
          return
        update()
      }
    }, period)
  }

  def void update(){
    log.info("Update started")
    entityUpdaters.each {
      def long lastRun = store.getLastRun(it.updaterKey())
      def UpdateStatus st = it.update(lastRun)
      store.save(it.updaterKey(),st)
    }
  }

  def void period(int period) {
    this.period = period
    scheduleUpdate()
  }

  def void start(){
    run = true
    scheduleUpdate()
  }
  def void stop(){
    run = false
  }

}
