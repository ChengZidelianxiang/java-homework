@echo off
chcp 65001 >nul
cd /d D:\SchoolProgramTest\JavaTest\Java6.15Test1
echo 正在编译...
"C:\Program Files\Java\jdk-17\bin\javac" -encoding UTF-8 -cp ".;mysql-connector-j.jar" -d "out\production\Java6.15Test1" GetDBConnection.java Example11_4.java
if %errorlevel% equ 0 (
    echo 编译成功！
    echo 正在运行...
    "C:\Program Files\Java\jdk-17\bin\java" -cp "out\production\Java6.15Test1;mysql-connector-j.jar" Example11_4
) else (
    echo 编译失败！
    pause
)
pause
