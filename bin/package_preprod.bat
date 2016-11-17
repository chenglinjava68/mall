@echo off
echo [INFO] Package the war in target dir.

cd /d %~dp0
cd ..
call mvn  clean package -Dmaven.test.skip=true -Dmaven.compile.fork=true -Ppreprod
cd bin
pause