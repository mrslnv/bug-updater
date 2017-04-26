package ali.bugupdater

import org.springframework.context.support.ClassPathXmlApplicationContext

class Main {

  static Console cons = System.console()
  static def readPassword = {
    def pswd = cons.readPassword('Enter password: ')
    if (!pswd)
      return false
    System.properties.put("rest.password", String.valueOf(pswd))
    true
  }
  static def readProperty = {key,value ->
    value = cons.readLine("Enter a ${key}[${value}]: ")
    if (value)
      System.properties.put(key, String.valueOf(value))
  }

  static def properites = [
          "rest.username":"miroslav.novak_hp.com",
          "rest.host":"mydtqc01.isr.hp.com",
          "runner.period":"60000"
  ]
  static def readProperties = {
    properites.each {entry->Main.readProperty(entry.key,entry.value)}
  }

  public static void main(String[] args) {


    if (cons == null) {
      println "No Console. Using parameters from properties."
    } else {
      def keepReading = !readPassword()
      while (keepReading) {
        readProperties()
        keepReading = !readPassword()
      }
    }
    def context = new ClassPathXmlApplicationContext("AppContext.xml")
    def Runner runner = (Runner) context.getBean("runner")
    println "Starting..."
    runner.start()
  }
}
