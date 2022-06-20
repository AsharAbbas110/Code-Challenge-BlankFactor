1. Framework used - Selenium Webdriver with Junit
2. Language used - Java
3. Libraries used - Selenium, Selenium Webdriver, JUnit
4. Executable used - chromedriver version 100. May need to update based on the browser version it's running.
5. To run automation right click on the class Testcases --> Runas --> Junit. This will run all the test cases
6. JRE and external JARs need to be updated in the build path. I have used JRE 1.5, it may vary based on the JRE installed in the system.
7. External JARs need to be removed and readded from the path src-->jars

Note -
1. Framework can be updated to support POM and other OOPS concepts when more pages are automated. Currently, only one page is automated so all the Test cases, methods, and page objects are in a single class to make it easy to understand.
2. Also, more test cases can be automated but the requirement is not clear to understand the extent of automation needed
