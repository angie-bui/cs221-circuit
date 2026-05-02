****************
* Circuit tracer
* CS221
* May 1, 2026
* Angie Bui
**************** 

OVERVIEW:

 This program finds the shortest path between the starting and end points in a text file and displays it for the user. 


INCLUDED FILES:

 * README - this file
 * CircuitBoard.java - source file
 * CircuitTracer.java - source file
 * CircuitTracerGUI.java - file for the GUI
 * CircuitTracerTester.java - tester class for source files
 * InvalidFileFormatException.java - exception for invalid formatting
 * OccupiedPositionException.java - exception for position occupied
 * Storage.java - stack/queue usage
 * TraceState.java - stores circuit board paths
 * boards/ - folder of circuit board text files to test for validity


COMPILING AND RUNNING:

 Compile with the command:
 $ javac *.java

 Run the compiled class file with the command:
 $ java CircuitTracerTester
            OR
 $ java CircuitTracer <-s|-q> <-c|-g> <inputfile>

 Console output will give the results after the program finishes.


PROGRAM DESIGN AND IMPORTANT CONCEPTS:

 The main concepts of the program was to be able to scan through and check the format validity before reading in the variables from the text file. It is important to make sure that the characters used in the file were allowed characters, so that later on there wouldn't be issues looking around. Another main concept was to make sure that the search continued within the bounds of the circuit board. If we didn't check that, then the board would be looping pretty much forever. 
 
 The organization of the program worked with the board first and making sure the methods of the board are valid. I also used a CircuitTracer class which kept track of all of the best paths of the circuit board. If there was a shorter path that was found, then the list of best paths would be replaced by the shortest found path. The classes work together by organizing the board and keeping track of points, searching through the board, and then displaying the trace afterward to the user. 


ANALYSIS:

Stack vs. queue:
Stack (LIFO) goes to the most recently discovered path and commits early to one path before it hits a dead end or a solution. It finds longer paths sooner.
Queue on the other hand (FIFO) looks at all of lengths of specific paths before it goes on to longer paths. It looks for shorter paths first.

Choosing the total number of search states (possible paths) is not affected by the choice of stack or queue. Both of these will generate the same amount of paths. The only difference is what the order of the explored paths are and how many states were explored before a solution was found.  

If the solution is short, then the queue will find the solution in the fewest steps. The stack can find a solution faster if that solution happens to be one that it explores first. Neither is "always" better though.

For the queue, it does guarantee that the first solution found will be the shortest path since it looks through paths by their length. On the other hand, the stack does not guarantee this because it could be exploring a longer solution beforre it looks at a shorter one. 

The stack has a lower memory usage than the queue because the stack only stores the current path and some other paths so memory grows pretty linear. However, the queue stores all of the paths with the current shorest length that it is looking at, so it is very memory intensive compared with the stack. 

The Big-O is O(4^n) for the search algorithm because there are 4 directions that it is looking in and n is the steps that it is taking. 

The order reflects the total number of paths explored in the worst case. As you continue through the search, the paths grow exponentially.

'n' is the size of the board or size of the elements. 


TESTING:

 To test this program, I mainly used the CircuitTracerTester class to see which tests were not passing and how to fix those with the board folder's invalid and valid text files. Some issues that popped up in the code while working on the project were logic errors while making sure that the text file was in a valid format, using the search to check each direction from the starting point to get to the ending point while also staying within bounds. Another error that I got while working on the project was the OccupiedPositionException where I had to surround storing the currentState to the Storage with a try catch to not let the OPE crash the code. 


DISCUSSION:

Some of the challenges with this project were the tester class and having a lot of the scenarios fail. To solve this I looked back on the logic and the methods of my classes to check where the error is. I debugged the code and used breakpoints to try and figure out where the code didn't run the way I intended. Another challenge was that the GUI test classes at first were not running. I realized that instead of clicking File and Quit, I actually had to wait out for the GUI to exit itself in order to get those tests to pass. 
 
 
EXTRA CREDIT:

CircuitTracerGUI class to display the GUI when the user selects for the output to be displayed that way. 