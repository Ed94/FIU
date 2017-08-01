Completed by Edward R. Gonzalez 

July 17, 2017

Files:
Maze.java
Description:
    Contains all code related to the completing the escape function.
Known Problems:
    Even with multiple checks, I was not able to fully trouble shoot the multiple
    stackOverflow errors present within the process of the recursion. Either the history
    stack or the options stack would somehow pass conditionals they are not intended to pass,
    as found through debugging with multiple breakpoints.
    In order to allow the program to still complete its tasks, known areas of code are surrounded by tryCatches.

MazeDemo.java
Description:
    Same code as provided with added printPath function added after the escape functions.
    
MazeInputDriver.java
Description:
    Contains functionality to read and execute the Maze escape function on maze's within a properly formated txt file.
    All three arguments work properly. The map is printed after importation if the user requests it and the path prints
    after the escape function is completed.

Maze Files:
Maze1.txt, Maze2.txt, Maze3.txt, Maze4.txt, Maze5.txt
    
A cmd shortcut called Maze.cmd shorty was added for convience as it automatically opens to the current path.

To use MazeInputDriver.java: (Assuming java Path is in system Path for windows)
- Open the shortcut to the cmd.
- Compile the code with command: javac MazeInputDriver.java
- Run the code with command: java MazeInputDriver -info:"filename" -printpath:"yes or no" -printmaze:"yes or no"