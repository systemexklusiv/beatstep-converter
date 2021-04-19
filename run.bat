@echo.
@echo.
rmdir /S /Q target
@echo.
call mvn clean package
@echo.
@echo on
java -jar target/beatstep-converter-1.0.0.jar
