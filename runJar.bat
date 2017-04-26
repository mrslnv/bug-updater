svn up
call mvn install -DskipTests
cd target
java -Dlog4j.configuration=log4j.properties -jar bug-updater.jar 