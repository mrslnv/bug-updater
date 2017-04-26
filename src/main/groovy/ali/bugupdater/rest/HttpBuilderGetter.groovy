package ali.bugupdater.rest;


import groovy.util.slurpersupport.GPathResult
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import java.text.MessageFormat
import org.apache.abdera.protocol.client.ClientResponse
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

public abstract class HttpBuilderGetter extends AbstractGetter {
  private final Log log = LogFactory.getLog(HttpBuilderGetter.class);

  def builder

  public RestGetter(){
  }

  private def logErrorResponse(ClientResponse resp) {
    log.error("Status:" + resp.getStatusText());
    try {
      log.error("Details: " + resp.getInputStream().getText())
    } catch (any) {
      log.error("Error reading details:", any)
    }
  }

  protected getEntities(String path, String query, long lastRun) throws Exception {

    mappingNotNull()

    def count = 0, totalCount = 1
    def result = []
    for (def startIndex = 1;startIndex <= totalCount;startIndex += count){

      def date = new Date(lastRun).format("yyyy-MM-dd HH:mm:ss")
      query = query.replace("{0}",date)
      def url = new URI("http",null,host,Integer.parseInt(port),path,query+"&page-size=${pageSize}&start-index=${startIndex}&fields=" + entityMapping.mapping.keySet().join(","),null)
      log.debug "Request: $url"
      builder = new HTTPBuilder()
      builder.auth.basic(host, Integer.parseInt(port),userName, password)
      builder.get(uri: "http://${host}:${port}/qcbin/authentication-point/authenticate")
      def node = builder.get (uri:url,contentType:"application/xml"){
        resp, node ->
        node
      }
      totalCount = node.@TotalResults.toInteger()
      count = node.Entity.size().toInteger()
      result << node
    }
    result
  }
}