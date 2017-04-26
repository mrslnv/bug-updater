package ali.bugupdater.db.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import org.hibernate.validator.constraints.Length
import javax.persistence.ManyToOne
import javax.persistence.JoinColumn
import ali.bugupdater.common.UpdateStatus
import ali.bugupdater.common.EntityUpdateStatus
import javax.persistence.SequenceGenerator
import ali.bugupdater.db.RunnerStore

@Table(name = "Run_Entity")
@Entity
@SequenceGenerator(name="run_seq", sequenceName="mrun_sequence")
class RunEntity {
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="run_seq")
  @Column(name = "ID")
  int id;

  @Column(name = "LAST_RUN")
  @Length(max=20)
  String lastRun;
  //2011-01-01 12:12:12

  @Column(name = "NEW_RUN")
  @Length(max=20)
  String newRun;
  //2011-01-01 12:12:12

  @Column(name = "INSERTS")
  int inserts;

  @Column(name = "UPDATES")
  int updates;

  @ManyToOne
  @JoinColumn(name="UPDATER")
  UpdaterEntity updater;

  def RunEntity(){
  }
  def RunEntity(UpdateStatus s){
    lastRun = RunnerStore.formatDate(s.lastRun)
    newRun = RunnerStore.formatDate(s.newRun)
    inserts = s.status(EntityUpdateStatus.INSERT)
    updates = s.status(EntityUpdateStatus.UPDATE)
  }

  def void setNewRun(long time){
    this.newRun = RunnerStore.formatDate(time)
  }
  def void setLastRun(long time){
    this.lastRun = RunnerStore.formatDate(time)
  }

}
