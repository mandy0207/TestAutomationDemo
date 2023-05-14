cd ..
mvn dependency:resolve
mvn clean install test -Dsurefire.suiteXmlFiles=src/test/resources/suites/micase.setup.testng.xml