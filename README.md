The steps regarding how to build and run this application

Step 1: Install MySQL database, Maven, Java 11.

Step 2: Run db/generic_workflow_ddl.sql script 

Step 3: Change database IP address, username and password in src\main\resources\application.properties

Step 4: Run command 'mvn clean spring-boot:run' in current directory

Step 5: After application start running, access URL http://localhost:8080/swagger-ui.html