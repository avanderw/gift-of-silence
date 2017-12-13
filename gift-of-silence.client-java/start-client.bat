@echo off
if not exist ".classpath" call .classpath-create.bat
set /p cp=<.classpath
java -classpath ".\target\classes;.\target\test-classes;%cp%" gift.of.silence.Console
pause