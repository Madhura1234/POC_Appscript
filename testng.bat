


ECHO OFF 


cd C:\clsFRameWork\CLS_PoC\Test_01\Framework
set classpath=C:\clsFRameWork\CLS_PoC\Test_01\Framework\bin;
set classpath=C:\clsFRameWork\CLS_PoC\Test_01\Framework\lib\*;
java -cp bin;lib/* org.testng.TestNG testng.xml

 
PAUSE
