package lightsOut;

import java.util.Random;

public class LightsOutModel
{
    /** Provides the rows and col layout of the board */
    private int[][] boardLayout;
    
    private int[][] recordBoard;

    /** Counts how many games have been won */
    private int winCount;

    /**
     * Creates a Lights Out board with the specified number of rows and columns. All lights should be on
     * 
     * If either rows or cols is less than 1, throws an IllegalArgumentException.
     */
    public LightsOutModel (int rows, int cols)
    {
        if (rows < 1 || cols < 1)
        {
            throw new IllegalArgumentException();
        }

        boardLayout = new int[rows][cols];
        recordBoard = new int [rows][cols];
    }

    /**
     * When new game is started, all lights are turned off. Random integer between 0 and 1 are used to randomly generate
     * a set of moves. Player essentially back-solving a series of computer generated moves. This is to prevent
     * unsolvable games.
     */
    public void newGame ()
    {
        // Turns all the lights on the board off
        Random rand = new Random();
        for (int i = 0; i < boardLayout.length; i++)
        {
            for (int x = 0; x < boardLayout[i].length; x++)
            {
                boardLayout[i][x] = 1;
                recordBoard[i][x] = 0;
            }
        }

        // Uses Random to randomly make a move
        for (int i = 0; i < boardLayout.length; i++)
        {
            for (int x = 0; x < boardLayout[i].length; x++)
            {
                if (rand.nextInt(2) == 0)
                {
                    move(i, x);
                    recordBoard[i][x] = 1;
                }
            }
        }
    }

    /**
     * Records a move made by the player. A single "move" switches maximum of 5 squares. The top, bottom, left and right
     * and the square that was clicked on. However if the square that was clicked on was a corner or edge, only 3 or 4
     * squares will be switched respectively.
     */

    public int move (int row, int col)
    {
        lightSwitch(row, col); // center
        lightSwitch(row, col - 1); // left
        lightSwitch(row - 1, col); // bottom
        lightSwitch(row, col + 1); // right
        lightSwitch(row + 1, col); // top
        return checkLightsOut(boardLayout) ? 1 : 0; // After each move, check if all the lights on the board are turned
                                                    // "off."
    }

    /**
     * Checks the switch state of the square and turns it on or off. The method itself doens't know if the square
     * clicked on is a edge corner or normal square. This method simply determines if squares to the top, left, bottom,
     * and right exist.
     * 
     * @param row
     * @param col
     */
    public void lightSwitch (int row, int col)
    {
        if ((row >= 0 && col >= 0) && (row <= boardLayout.length - 1 && col <= boardLayout[row].length - 1))
        {
            boardLayout[row][col]++; // Increments the value stored at boardLayout by 1.
        }
    }

    /**
     * Checks whether or not all the lights are turned off on the board, if true increments winCount by 1.
     * 
     * @param A
     * @return
     */
    public boolean checkLightsOut (int[][] A)
    {
        for (int i = 0; i < A.length; i++)
        {
            for (int j = 0; j < A[i].length; j++)
            {
                if ((A[i][j]) % 2 != 1)
                {
                    return false;
                }
            }
        }
        winCount++;
        return true;
    }

    /**
     * Returns the occupant of the square at the specified row and column. If the row or column doesn't exist, throws an
     * IllegalArgumentException. Returns whether or not the light is turn on (1) or off(0).
     */
    public int getOccupant (int row, int col)
    {
        try
        {
            int occupant = boardLayout[row][col];
            return (occupant % 2);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new IllegalArgumentException();
        }
    }
    
    public int playBack (int row, int col)
    {
        try
        {
            int step = recordBoard[row][col];
            return (step%2);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Reports how many wins have occurred since this board was created.
     */
    public int getWins ()
    {
        return winCount;
    }
}
