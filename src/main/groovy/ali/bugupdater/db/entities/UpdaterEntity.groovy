package ali.bugupdater.db.entities

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Column
import javax.persistence.Id
import org.hibernate.validator.constraints.Length
import javax.persistence.OneToMany
import javax.persistence.FetchType
import javax.persistence.UniqueConstraint
import javax.persistence.SequenceGenerator
import ali.bugupdater.db.RunnerStore

@Table(name = "Updater_Entity", uniqueConstraints = @UniqueConstraint(columnNames = ["INSTANCE", "KEY"]))
@Entity
@SequenceGenerator(name = "updater_seq", sequenceName = "mupdater_sequence")
class UpdaterEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "updater_seq")
  @Column(name = "ID")
  int id;

  @Column(name = "KEY")
  @Length(max = 10)
  String key;

  @Column(name = "INSTANCE")
  @Length(max = 100)
  String instance;

  @Column(name = "LAST_RUN")
  @Length(max = 20)
  String lastRun;
  //2011-01-01 12:12:12

  @OneToMany(mappedBy = "updater", fetch = FetchType.LAZY)
  Set<RunEntity> runs;

  def void setLastRun(long time) {
    this.lastRun = RunnerStore.formatDate(time)
  }

  void addRun(RunEntity runEntity) {
    if (runs == null)
      runs = new HashSet<RunEntity>(1)
    runs.add(runEntity)
    runEntity.updater = this
  }
}
