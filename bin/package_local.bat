@echo off
echo [INFO] Package the war in target dir.

cd /d %~dp0
cd ..
call mvn  compile package -Dmaven.test.skip=true -Dmaven.compile.fork=true -Plocal
cd bin
pause
