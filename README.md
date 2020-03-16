# Numbrix
 
## File Formatting

First line is size, n, of the matrix
The next rest of the file consists of n lines that have n entries of numbers with commas seperating them, including a trailing comma. Empty spaces can be written as empty space or 0.

    9
    37,  ,31,  ,21,  , 3,  , 5,
      ,35,  ,29,  ,19,  , 1,  ,
    39,  ,  ,  ,  ,  ,  ,  , 7,
      ,43,  ,  ,  ,  ,  ,11,  ,
    41,  ,  ,  ,  ,  ,  ,  ,13,
      ,47,  ,  ,  ,  ,  ,65,  ,
    49,  ,  ,  ,  ,  ,  ,  ,67,
      ,51,  ,79,  ,81,  ,71,  ,
    53,  ,55,  ,77,  ,75,  ,73,

## Heuristics
### Shortest Manhattan Distance
Chooses next node from the set of neighboring nodes based on which node has the shortest Manhattan distance to the next smallest clue.
### Closest Manhattan Distance
Chooses next node from the set of neighboring nodes based on which node has the closest Manhattan distance to the numerical distance between its value and the next smallest clue's value.
### Random Neighbor
Chooses the next node from the set of neighboring nodes in a random order.
## Output
Prints out the clue board.
Prints out the solved board, or no solution.

## Included Example Boards
numbrix1.txt - easy 9x9 board from official website

numbrix2.txt - easy 3x3 board

numbrix3.txt - unsolveable 4x4 board

numbrix4.txt - expert 9x9 board from official website

numbrix5.txt - 20x20 board with X pattern of clues (self-created)

numbrix6.txt - 20x20 board with reduced X pattern of clues (self-created, takes about 20-30 seconds)
