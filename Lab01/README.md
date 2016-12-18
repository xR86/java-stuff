# java-stuff
Advanced Programming Lab Homeworks

### Lab description

Write a Java application that generates and prints on the screen Graeco-Latin squares. A Graeco-Latin Square is a n x n matrix. Each cell of the matrix is a pair of elements taken from the carthesian product of two given sets: S X T; for example, S may be a subset of the Latin alphabet and T a subset of the Greek alphabet.

The constraints are:
+ In each row, and in each column, all the 2n elements are different.
+ No two pairs are the same in the matrix.

Example:  
![Latin Square](LatinSquare.png)


The main specifications of the application are:

+ Create and display the square from the example. The Unicode character for α is "\u03B1".
+ Parse the command line arguments - they are given in the following format: 
  + [n:number of elements] [S:"elements of the first set"] [T:"elements of the second set"]
If command line arguments are missing, the application will generate a random value for n, S contains the first n characters of the Latin alphabet (upper case) and T the first n characters of the Greek alphabet (lower case).
+ Generate Graeco-Latin squares of size n x n and display them on the screen:
+ at least one...
+ all of them (display only the first, count them instead).
The running time of the application will be displayed on the screen (try n=5).