@echo off
chcp 65001 >nul
echo 正在清空 mess 表...
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p123456 students -e "DELETE FROM mess;"
echo 完成！可以运行程序了。
pause
