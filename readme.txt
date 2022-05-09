Files in Submission:
Summary.docx  - This is the word document Summary for the project.
CPU.java      - This file contains the CPU class that reads from the memory and executes instructions.
Memory.java   - This file contains the memory class that reads the inputs from a file and stores them in an array.
Project1.java - This file contains the Project1 class that starts the process and reads the command line arguments.
sample5.txt   - This file contains my test case. The output should be an image of a rabbit using ASCII values and then "CS-4348". 



Compile and Run instructions:

1. Ensure you hava a java JDK installed on the system.
2. type: javac CPU.java
3. type: javac Memory.java
4. type: javac Project1.java
5. to run the program type: java Project1 <testFile> <timerNumber>. You can replace <testfile> with any of the test files.
The test file I made is called sample5.txt. So an example would be "java Project1 sample5.txt 25". If a timer value is
not provided, the program will set a default timer value to 20 and display that to the screen.