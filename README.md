# Numbrix
 
## file Formatting

first line is size, n, of the matrix
the next rest of the file consists of n lines that have n entries of numbers with commas seperating them. Include a trailing comma. Empty spaces can be written as empty space or 0.

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
Chooses next node from the set of neighboring nodes based on which node has the shortest manhattan distance to the next smallest clue.
### Closest Manhattan Distance
chooses next node from the set of neighboring nodes based on which node has the closest manattan distance to the numerical distance between its value and the next smallest clue's value.
### Random Neighbor
chooses the next node from the set of neighboring nodes in a random order.
## Output
prints out the clue board.
prints out the solved board, or no solution.
