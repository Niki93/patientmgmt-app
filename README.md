Running the Application
1)	Import the maven project into an IDE and run the PatientmgmtAppApplication class
2)	From the terminal, navigate to the project root /patientmgmt-app, and type mvn spring-boot:run

Postman script
Postman script ‘Clinical Portal.postman_collection.json’ can be found under the resources folder and used to test the 3 APIs

Junit Test Coverage
How to obtain it?
At the terminal, at the project root /patientmgmt-app
1)	run mvn clean test
2)	run mvn jacoco:report
3)	navigate to the target folder and look for index.html at /site/jacoco/
