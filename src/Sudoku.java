package Sudoku;

import java.util.Scanner;

/**
 * Created by Jarvis on 9/23/17.
 */
public class Sudoku
{
    private int[][] puz;
    private int size;
    private int box_r;
    private int box_c;

    public Sudoku(int s, int r, int c)
    {
        size = s;
        puz = new int[size][size];
        box_r = r;
        box_c = c;
    }

    public int getBox_r()
    {
        return box_r;
    }

    public int getBox_c()
    {
        return box_c;
    }

    public void loadSudoku(String puzzle)
    {
        Scanner scan = new Scanner(puzzle);
        while (!scan.hasNextInt())
            scan.nextLine();
        while (scan.hasNextInt())
            for (int row = 0; row < size; row++)
                for (int column = 0; column < size; column++)
                    puz[row][column] = scan.nextInt();
        scan.close();
    }

    public boolean solve(int row, int col)
    {
        if (col == size) {                                  // If column is 9th, we make it 0 and increase row number
            col = 0;                                        // If row becomes 9, that means we are in the last cell
            row+=1;                                         // and our work is done so we return true.
            if (row == size) {
                return true;
            }
        }

        for (int num = 1; num < size+1; num++)              // For numbers between 1 to 9, we start picking up values.
        {
            if (puz[row][col] == 0)                     // If the entry in our matrix[row][col] is 0, that means its
            {                                               // empty and we try to fill it with the above value.
                if (isLegal(row, col, num))                 // If that value is legal, fill it and the call the function
                {                                           // again on the next cell.
                    puz[row][col] = num;
                    if (solve(row, col + 1))            // We induce a recursive method that tries to fill each cell
                    {                                       // with a valid number till we reach the end. If no value in
                        return true;                        // that cell is legal, our function returns false which
                    }                                       // transfers control to the recursive call of the previous
                    else                                    // function call and sets the value in it back to zero and
                    {                                       // and starts filling in values from where it left off.
                        puz[row][col] = 0;              // This continues till we reach the last cell which if it
                    }                                       // finds a legal value, fills it and that call returns true
                }                                           // which collapses all previous calls in one shot and returns
            }                                               // true.
            else
                return solve(row, col + 1);             // If that cell is not empty, skip it.
        }
        return false;                                       // No solution was found therefore return false.
    }
    // We Looped through all the cells and were able to fill
    // it out. So we return true.

    public boolean isLegal(int row, int col, int num)
    {
        for (int j = 0; j < size; j++)                      // Check for same number in row
        {
            if (puz[row][j] == num) {return false;}
        }

        for (int r = 0; r < size; r++)                      // Check for same number in column
        {
            if (puz[r][col] == num) {return false;}
        }

        int topRightRow = (row / getBox_r()) * getBox_r();           // Check for same number in that box.
        int topRightCol = (col / getBox_c()) * getBox_c();
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (puz[topRightRow+i][topRightCol+j] == num)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public String printSudoku()
    {
        int r = 0;
        int c = 0;
        String repeatedStar = new String(new char[3*puz.length-2]).replace('\0', '-');
        String s = "";
        for (int[] i: puz)
        {
            if (r % box_r == 0)
                s += repeatedStar+'\n';
            for(int j: i)
            {
                if (c % box_c == 0)
                    s += '|';
                if (j < 10)
                    s += "  " + j + " ";
                else
                    s += " " + j + " ";
                c++;
            }
            r++;
            s += "|";
            s += '\n';
        }
        s += repeatedStar+'\n';
        return s;
    }
}
