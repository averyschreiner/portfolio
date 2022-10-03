/**
 * Class: 44-242 Data Structures
 * Author: Avery Schreiner
 * Description: project 2 sudoku
 * Due: 7 Nov 2021
 * I pledge that I have completed the programming assignment independently.
   I have not copied the code from a student or any source.
   I have not given my code to any other student.
   I have not given my code to any other student and will not share this code
   with anyone under any circumstances.
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SudokuBoard
{
    private final int[][] board;
    
    public SudokuBoard(String fname) throws FileNotFoundException
    {
        board = new int[9][9];
        load(fname);
    }
    
    public void load(String fname) throws FileNotFoundException
    {
        //estable board input and readalbe file
        File inputFile = new File(fname);
        Scanner readFile = new Scanner(inputFile);
        
        //iterate through and assign values to indexes of 2d array board
        for (int r = 0; r < 9; r++)
        {
            for (int c = 0; c < 9; c++)
            {
                board[r][c] = readFile.nextInt();
            }
        }
    }
    
    public void print()
    {
        for (int i=0; i<9; i++)
        {
            if (i%3 == 0)
                printHdiv();
            for (int j=0; j<9; j++)
            {
                if (j%3 == 0)
                    System.out.print("| ");
                System.out.print(board[i][j]);
                System.out.print(" ");
            }
            System.out.println("|");     
        }
        printHdiv();
    }
    
    private static void printHdiv()
    {
        int DIV_LEN = 25;
        // divider
        for (int i=0; i<DIV_LEN; i++)
            System.out.print('-');
        System.out.println("");
    }
    
    private boolean allowedInRow(int row, int value)
    {
        //iterate through row and see if value is equal to any value in row
        for (int i = 0; i < 9; i++)
        {
            if (value == board[row][i])
            {
                return false;
            }
        }
        
        //if value isn't in row
         return true;
    }
    
    private boolean allowedInCol(int col, int value)
    {
        //iterate through col and see if value is equal to any value in col
        for (int i = 0; i < 9; i++)
        {
            if (value == board[i][col])
            {
                return false;
            }
        }
        
        //if value isn't in col
        return true;
    }
    
    private boolean allowedInBlock(int row, int col, int value)
    {
        //get row and col start vals
        int blockRowStart = row / 3 * 3;
        int blockColStart = col / 3 * 3;
        
        //iterate through block
        for (int r = blockRowStart; r < blockRowStart + 3; r++)
        {
          for (int c = blockColStart; c < blockColStart + 3; c++)
          {
              if (value == board[r][c])
              {
                  return false;
              }
          }
        }
        
        //if val isn't in block
        return true;
    }
    
    private boolean allowed(int row, int col, int value)
    {
        return allowedInRow(row, value) && allowedInCol(col, value) && 
                allowedInBlock(row, col, value);
    }
    
    // solution functions
    public boolean solve()
    {
        return solve(0,0);
    }
    
    public boolean solve(int row, int col)
    {
        //base cases
        //have reached the end of the columns, restart at column 0 and at the next row
        if (col == 9)
        {
            col = 0;
            row += 1;
        }
        //if last row is finished, board is solved
        if (row == 9)
        {
            return true;
        }
        
        //if spot on board is empty
        if (board[row][col] == 0)
        {
            //iterate through possible numbers
            for (int i = 0; i < 9; i++)
            {
                //if number isn't in row column or block
                if (allowed(row, col, i+1))
                {
                    //this current spot on the board is equal to the iter
                    board[row][col] = i+1;
                    
                    //if the next empty spaces agrees with this assignment, return true
                    if (solve(row, col+1))
                    {
                        return true;
                    }
                    
                    //reset the board
                    board[row][col] = 0;
                }
            }
            
            //if no numbers can fill this spot, board is unsolvable 
            return false;
        }
        
        //if spot is occupied, try the next one
        else 
        {
            return solve(row, col+1);
        }
    }
    
    public static void main(String[] args)
    {
        try 
        {
            //create sudoku board 
            SudokuBoard b = new SudokuBoard("board1.txt"); 
            System.out.println("Unsolved Board 1:");
            b.print();
            b.solve();
            System.out.println("Solved Board 1:");
            b.print();
            
            //second board
            SudokuBoard c = new SudokuBoard("board2.txt");
            System.out.println("Unsolved Board 2:");
            c.print();
            c.solve();
            System.out.println("Solved Board 2:");
            c.print();
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e);
        }
    }
}
