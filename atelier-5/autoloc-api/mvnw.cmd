@echo off
setlocal
where mvn >nul 2>nul
if errorlevel 1 (
  echo Maven is not available in PATH. Install Maven or use IntelliJ IDEA's Maven integration. 1>&2
  exit /b 1
)
mvn %*
