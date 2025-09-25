@echo off
echo Building SwipeChef Android App...

REM Check if Gradle wrapper exists and is executable
if not exist "gradlew.bat" (
    echo Error: gradlew.bat not found. Please ensure Gradle wrapper is properly configured.
    pause
    exit /b 1
)

REM Build debug APK
echo Building debug APK...
call gradlew.bat assembleDebug

if %ERRORLEVEL% NEQ 0 (
    echo Build failed! Please check the error messages above.
    pause
    exit /b 1
)

REM Check if APK was created
set APK_PATH=app\build\outputs\apk\debug\app-debug.apk
if not exist "%APK_PATH%" (
    echo Error: APK not found at %APK_PATH%
    pause
    exit /b 1
)

REM Copy to shared drive
set SHARED_DRIVE=G:\Shared drives\Stream Stage Company Wide\CCApks
if exist "%SHARED_DRIVE%" (
    set APK_NAME=SwipeChef-debug-%DATE:~10,4%%DATE:~4,2%%DATE:~7,2%-%TIME:~0,2%%TIME:~3,2%%TIME:~6,2%.apk
    set APK_NAME=%APK_NAME: =0%
    echo Copying APK to shared drive as %APK_NAME%...
    copy "%APK_PATH%" "%SHARED_DRIVE%\%APK_NAME%"
    if %ERRORLEVEL% EQU 0 (
        echo ✓ APK successfully copied to: %SHARED_DRIVE%\%APK_NAME%
    ) else (
        echo ✗ Failed to copy APK to shared drive
    )
) else (
    echo ⚠ Shared drive not accessible: %SHARED_DRIVE%
    echo APK available at: %APK_PATH%
)

echo.
echo Build completed successfully!
echo APK location: %APK_PATH%
pause