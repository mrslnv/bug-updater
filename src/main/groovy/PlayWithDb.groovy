import groovy.sql.GroovyRowResult
import groovy.sql.Sql

def url = "jdbc:oracle:thin:@52.17.213.249:1521:AGM01"
def username = "ota_tests"
def db = Sql.newInstance(url, username, username, "oracle.jdbc.OracleDriver")
def dbName = "DEFAULT_DEMO2_DB"

def queryStmt = "select max(TNPA_ID) as maxid from \"DEFAULT_DEMO2_DB\".\"TEST_NEW_ENT_PRODUCT_AREA\""
println(queryStmt)

long tcSeq = 10001
def row = db.firstRow(queryStmt)
if (row.maxid != null)
    tcSeq = row.maxid

println "Last tc ID: $tcSeq"


def testId = 1390
def paId = 1003

 def ins = "INSERT INTO \"DEFAULT_DEMO2_DB\".\"TEST_NEW_ENT_PRODUCT_AREA\"\n" +
        " (TNPA_ID,TNPA_TEST_ENTITY_ID,TNPA_PRODUCT_AREA_ID) VALUES ($tcSeq,$testId,$paId)"

println ins

queryStmt = "INSERT INTO \"DEFAULT_DEMO2_DB\".\"TEST_NEW_ENT_PRODUCT_AREA\"\n" +
        " (TNPA_ID,TNPA_TEST_ENTITY_ID,TNPA_PRODUCT_AREA_ID) VALUES (9999,1336,1001)"

db.execute(queryStmt.toString())