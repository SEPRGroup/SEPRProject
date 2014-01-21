@echo off
TITLE Find strings

set searchStr={!}
set filePath=%USERPROFILE%\Documents\GitHub\SEPRProject\Code
set fileExt=*.java
:start

echo Files containing
echo %searchStr%
echo.
FINDSTR /L /N /S "%searchStr%" "%filePath%\%fileExt%"
echo.
Pause
echo __________
echo.

GOTO start
