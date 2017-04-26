package ali.bugupdater.common


class UpdateStatus {
  def count = [:]
  def long lastRun
  def long newRun
  def UpdateStatus(long lastRun){
    this.lastRun = lastRun
    this.newRun = System.currentTimeMillis()
  }
  def void add(EntityUpdateStatus s) {
    def c = count[s];
    if (c == null)
      count[s] = 1
    else
      count[s] = c+1
  }

  int status(EntityUpdateStatus s) {
    def c = count[s]
    if (c == null)
      return 0
    return c
  }

  def String toString() {
    return count.toString();    
  }

}
