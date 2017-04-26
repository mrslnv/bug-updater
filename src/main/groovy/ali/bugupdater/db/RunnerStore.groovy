package ali.bugupdater.db

import ali.bugupdater.common.UpdateStatus
import ali.bugupdater.db.entities.UpdaterEntity
import org.hibernate.SessionFactory
import org.hibernate.criterion.DetachedCriteria
import org.hibernate.criterion.Restrictions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.hibernate.Session
import ali.bugupdater.db.entities.RunEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.beans.factory.annotation.Value

@Component
class RunnerStore {
  private static final String INITIAL_DATE = "2010-01-01 00:00:00"

  @Autowired(required = true)
  private SessionFactory sessionFactory;

  @Autowired
  @Value('${runner.instance}')
  private String instance;

  private Session session;

  @Transactional
  def long getLastRun(String updaterKey) {
    def UpdaterEntity u = getUpdater(updaterKey)
    if (u != null)
      return parseDate(u.lastRun)
    return parseDate(INITIAL_DATE)
  }

  protected Session currentSession() {
      if (session != null) return session;
      return sessionFactory.getCurrentSession();
  }

  private def UpdaterEntity getUpdater(String updaterKey) {
    DetachedCriteria crit = DetachedCriteria.forClass(UpdaterEntity.class);
    crit.add(Restrictions.eq("key", updaterKey))
    crit.add(Restrictions.eq("instance", instance))
    (UpdaterEntity) crit.getExecutableCriteria(currentSession()).uniqueResult()
  }

  @Transactional
  def void save(String updaterKey, UpdateStatus updateStatus) {
    def UpdaterEntity u = getUpdater(updaterKey)
    if (u == null){
      u = new UpdaterEntity(key:updaterKey,instance:instance)
    }
    u.setLastRun(updateStatus.newRun)
    def run = new RunEntity(updateStatus)
    u.addRun(run)
    currentSession().save(u)
    currentSession().save(run)
  }

  static def String formatDate(long time) {
    new Date(time).format("yyyy-MM-dd HH:mm:ss")
  }

  static def long parseDate(String time) {
    Date.parse("yyyy-MM-dd HH:mm:ss",time).getTime()
  }

}
