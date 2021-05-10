This repository contains the code for a project scheduling and time management application implemented for course 02161 Software Engineering 1 at DTU. This repository contains the program developed by group 14.

The application uses the model-view-controller design pattern to separate layer and application layer.

# Running with Eclipse
To run the program, clone this project into a local folder. Import the project in Eclipse by choosing "File" -> "Open projects from File System" and selecting the folder that the project was cloned into.

## Tests
To run the tests, in Eclipse, navigate to `src/tests/java/schedulingapp`, right-click on the file `UnitTest.java` and choose "Run as" -> "JUnit test"

## User interface
To run the program with its supplied user interface, navigate to `src/main/java/ui` right-click on the file `Controller.java` and choose "Run as" -> "Java Application". The program starts up with a number of users pre-registered in the system when operating in demo-mode, for lack of a persistence layer. To navigate the program, log in with one of the registered users (eg. "huba" or "geog").
