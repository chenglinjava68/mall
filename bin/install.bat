@echo off
echo [INFO] install the jar or war in Local repository dir.

cd /d %~dp0
cd ..
call mvn clean install -Dmaven.test.skip=true
cd bin
pause