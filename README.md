# Artificial-Intelligence-8-Puzzle-Problem
8-Puzzle-Problem
Recursive Best First Search Algorithm:-
Similar to recursive depth-first search, but rather than continuing indefinitely down the current path, it uses the f-limit variable 
to keep track of the f-value of the best alternative path available from any ancestor of the current node. 
If the current node exceeds this limit, the recursion unwinds back to the alternative path. As the recursion unwinds, 
RBFS replaces the f-value of each node along the path with backed-up value—the best f-value of its children. 
In this way, RBFS remembers the f-value of the best leaf in the forgotten subtree and can therefore decide whether it’s worth re-expanding the subtree at some later time.
Hence depending on the application's  Time and Space requirements one of the above algorithms can be used.

1.Code is developed in Java using eclipse application on Windows 7 platform
2.The program can be run using comand line by passing programs javac file name 

Start State
8	7	6
5	4	3
2	1	0

Import the file AI.java and upload the sample start state in a file as input
if the start state data is solvable then it capture the number of node expanded and the depth of the search tree	
