package ali.bugupdater.mapping

abstract class EntityMapping {

  def mapping

  def idColumn

  def table

  def convertValue(value){
    // handle date
    if (value ==~ /[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]/){
      return Date.parse("yyyy-MM-dd",value).format("dd-MMM-yy")
    }
    value
  }

}
