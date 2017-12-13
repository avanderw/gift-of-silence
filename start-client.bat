echo off 
if not exist ".classpath" call .classpath-create
set /p cp=<.classpath
java -classpath .\target\classes;.\target\test-classes;%cp% gift.of.silence.core.Client
pause