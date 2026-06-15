@echo off
cd /d D:\SchoolProgramTest\JavaTest\Java6.15Test1
"C:\Program Files\Java\jdk-17\bin\javac" -encoding UTF-8 -cp ".;mysql-connector-j.jar" -d "out\production\Java6.15Test1" GetDBConnection.java Example11_4.java
if errorlevel 1 goto err
"C:\Program Files\Java\jdk-17\bin\java" -cp "out\production\Java6.15Test1;mysql-connector-j.jar" Example11_4
goto end
:err
echo COMPILE FAILED
pause
:end
pause
