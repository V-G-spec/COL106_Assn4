# COL106 - Assignment 4

## About

A Java program *assignment4.java*, that takes three command line arguments: two csv files (samples given below), 
and a function name. Based on the function name, the program output changes. For explanation purposes, 
a running example of Marvel character's graph taken. It is funny because at the time of uploading this file, 
my github profile picture is of Batman (Sorry Bob Kane, Frank Miller)
The Marvel Graph (Uploaded) has 2 files:
* nodes.csv: 327 nodes: Each node represents a character.
* edges.csv: 9891 edges (with weights): Edges between nodes/characters represents that the
characters/nodes have appeared together. Edge-weights are proportional to the number of co-occurrences


## Functions

* Average: Prints the average number of characters each Marvel character is
associated with, as a float upto two decimal places
Sample random output:
```
$java assignment4 nodes.csv edges.csv average
7.43
```

* Rank:  Prints a sorted list of all characters, with comma as delimiter (only comma,
as delimiter and no space). Sorting is in descending order of co-occurrence with other
characters. That is, characters with more co-occurrences appear before. If there is a tie between
characters based on co-occurrence count, then the order is descending based on
lexicographic order of the character strings.
Sample random output:
```
$java assignment4 nodes.csv edges.csv rank
Yogish,Riju,Rahul
```

* Function: independent_storylines_dfs: Implements DFS, then finds independent storylines,
that have no edge across them, using DFS. Prints the characters in each independent storyline, as a
separate line in the output.
The largest storyline (with maximum characters) appears at the top, followed by the second
largest and so on. Within each line, the character names should be delimited with comma, and
lexicographically sorted in descending order. If two storylines have same number of characters,
ties are broken in lexicographically descending order of character names.
Sample random output:
```
$java assignment4 nodes.csv edges.csv independent_storylines_dfs
Riju,Rahul
Yogish
```

## Author

Vansh Gupta: https://github.com/V-G-spec

## License

Copyright -2020 - Indian Institute of Technology, Delhi

Part of course COL106: Data Structures & Algorithms (Taught by Rahul Garg and Rijurekha Sen)
