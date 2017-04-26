import groovy.sql.Sql

def url = "jdbc:oracle:thin:@52.17.213.249:1521:AGM01"
def username = "ota_tests"
def db = Sql.newInstance(url, username, username, "oracle.jdbc.OracleDriver")
def dbName = "DEFAULT_DISCOVER_DB"

def queryStmt = "select TSN_TEST_ID, TRN_STATUS from \"DEFAULT_DISCOVER_DB\".\"TEST_NEW\" inner join \"DEFAULT_DISCOVER_DB\".\"TEST_RUN\" on TEST_RUN.TRN_TEST_ID = TEST_NEW.TSN_TEST_ID where TSN_MANUAL = 'N'"
println queryStmt
HashMap map = new HashMap()
db.eachRow(queryStmt, { row ->
    long testId = row.TSN_TEST_ID
    Test test = map.get(testId)
    if (test == null) {
        map.put(testId, new Test(testId, row.TRN_STATUS))
    } else {
        test.updateStatus(row.TRN_STATUS)
    }
})

HashSet passed = new HashSet()
HashSet skipped = new HashSet()
HashSet failed = new HashSet()

map.each { key, value ->
    switch (value.status) {
        case "Failed":
            failed.add(value)
            break
        case "Skipped":
            skipped.add(value)
            break
        case "Passed":
            passed.add(value)
            break
        default:
            println "Default $value"
    }
}

def all = []
all.add(passed)
all.add(skipped)
all.add(failed)

def AREA_IDs = [0, 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009, 1010, 1011, 1012]

long tcSeq = 10000

all.each {
    HashSet set = it

    def size = set.size()
    println "Size:" + size

    def step = Math.floor(size / AREA_IDs.size())
    AREA_IDs.add(0)
    AREA_IDs.each { area ->
        Iterator iter = set.iterator()
        for (int i = 0; i < step; i++) {
            if (!iter.hasNext())
                break;
            Test t = iter.next()
            def testId = t.id
            iter.remove();
            if (area != 0) {
                tcSeq++;
                def ins = "INSERT INTO \"DEFAULT_DISCOVER_DB\".\"TEST_NEW_ENT_PRODUCT_AREA\"\n" +
                        " (TNPA_ID,TNPA_TEST_ENTITY_ID,TNPA_PRODUCT_AREA_ID) VALUES ($tcSeq,$testId,$area)"
                println ins
                def ret = db.executeInsert(ins.toString())
                println "Insert: $ret"
            }
        }
    }
}


Object getRandomAreaId(AREA_IDs) {
    int idx = Math.floor(Math.random() * AREA_IDs.size())
    return AREA_IDs[idx]
}
