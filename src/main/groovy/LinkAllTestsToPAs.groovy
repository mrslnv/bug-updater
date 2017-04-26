import groovy.sql.Sql

def url = "jdbc:oracle:thin:@mpavm0083.hpswlabs.adapps.hp.com:1521:AGM01"
def username = "ota_tests"
def db = Sql.newInstance(url, username, username, "oracle.jdbc.OracleDriver")

/*select TSN_TEST_ID from "DEFAULT_DEMO_07_DB"."TEST_NEW"  T where not exists
(select 1 from DEFAULT_DEMO_07_DB.TEST_NEW_ENT_PRODUCT_AREA T where T.TSN_TEST_ID =  T.TNPA_TEST_ENTITY_ID)
*/

def queryStmt = "select max(TNPA_ID) as maxid from \"DEFAULT_DEMO_07_DB\".\"TEST_NEW_ENT_PRODUCT_AREA\""
println(queryStmt)
long tcSeq = db.firstRow(queryStmt).maxid
println "Last tc ID: $tcSeq"

queryStmt = "select TSN_TEST_ID from \"DEFAULT_DEMO_07_DB\".\"TEST_NEW\"  T where not exists\n" +
        "(select 1 from DEFAULT_DEMO_07_DB.TEST_NEW_ENT_PRODUCT_AREA T where T.TSN_TEST_ID =  T.TNPA_TEST_ENTITY_ID)"

def BASE = 102..113
def EXTRA = [1091,1092]
def AREA_IDs = []
AREA_IDs.addAll(BASE)
AREA_IDs.addAll(EXTRA)
AREA_IDs.addAll(EXTRA)
def ret = db.eachRow(queryStmt) {
    def testId = it.TSN_TEST_ID
    def paId = getRandomAreaId(AREA_IDs)
    tcSeq++;
    def ins = "INSERT INTO \"DEFAULT_DEMO_07_DB\".\"TEST_NEW_ENT_PRODUCT_AREA\"\n" +
            " (TNPA_ID,TNPA_TEST_ENTITY_ID,TNPA_PRODUCT_AREA_ID) VALUES ($tcSeq,$testId,$paId)"
    println ins
    def ret = db.executeInsert(ins.toString())
    println "Insert: $ret"
}

Object getRandomAreaId(AREA_IDs) {
    int idx = Math.floor(Math.random()*AREA_IDs.size())
    return AREA_IDs[idx]
}
