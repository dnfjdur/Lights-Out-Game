package lightsOut;

import java.util.Random;

public class LightsOutModel
{
    /** Provides the rows and col layout of the board */
    private int[][] boardLayout;

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
    }

    /**
     * When new game is started, all lights are turned off. Random integer between 0 and 1 are used to randomly generate
     * a set of moves. Player essentially back-solving a series of computer generated moves. This is to prevent
     * unsolvable games.
     */
    public void newGame ()
    {
        // Turns lights off
        for (int i = 0; i < boardLayout.length; i++)
        {
            for (int x = 0; x < boardLayout[i].length; x++)
            {
                boardLayout[i][x] = 1;
            }
        }

        // Basically a random move generator
        Random rand = new Random();
        for (int i = 0; i < boardLayout.length; i++)
        {
            for (int x = 0; x < boardLayout[i].length; x++)
            {
                if (rand.nextInt(2) == 0)
                {
                    move(i, x);
                }
            }
        }
    }

    /**
     * Records a move made by the player. First method checks for corner cases where only 3 squares are switch on or
     * off. Then checks for edge cases where 4 squares are switched. Finally any other move results in 5 squares being
     * switched
     */

    public int move (int row, int col)
    {
        if (row == 0)
        {
            if (col == 0)
            {
                lightSwitch(row, col);
                lightSwitch(row + 1, col);
                lightSwitch(row, col + 1);
                return checkLightsOut(boardLayout) ? 1 : 0;
            }
            else if (col == boardLayout[row].length - 1)
            {
                lightSwitch(row, boardLayout[row].length - 1);
                lightSwitch(row, boardLayout[row].length - 2);
                lightSwitch(row + 1, boardLayout[row].length - 1);
                return checkLightsOut(boardLayout) ? 1 : 0;
            }
            else
            {
                lightSwitch(row, col);
                lightSwitch(row, col - 1);
                lightSwitch(row + 1, col);
                lightSwitch(row, col + 1);
                return checkLightsOut(boardLayout) ? 1 : 0;
            }
        }
        else if (row == boardLayout.length - 1)
        {
            if (col == 0)
            {
                lightSwitch(row, col);
                lightSwitch(row - 1, col);
                lightSwitch(row, col + 1);
                return checkLightsOut(boardLayout) ? 1 : 0;
            }
            else if (col == boardLayout[row].length - 1)
            {
                lightSwitch(row, col);
                lightSwitch(row, col - 1);
                lightSwitch(row - 1, col);
                return checkLightsOut(boardLayout) ? 1 : 0;
            }
            else
            {
                lightSwitch(row, col);
                lightSwitch(row, col - 1);
                lightSwitch(row - 1, col);
                lightSwitch(row, col + 1);
                return checkLightsOut(boardLayout) ? 1 : 0;
            }
        }
        else if (col == 0)
        {
            lightSwitch(row, col);
            lightSwitch(row - 1, col);
            lightSwitch(row, col + 1);
            lightSwitch(row + 1, col);//
            return checkLightsOut(boardLayout) ? 1 : 0;
        }
        else if (col == boardLayout[row].length - 1)
        {
            lightSwitch(row, col);
            lightSwitch(row - 1, col);
            lightSwitch(row, col - 1);
            lightSwitch(row + 1, col);
            return checkLightsOut(boardLayout) ? 1 : 0;
        }
        else
        {
            lightSwitch(row, col);
            lightSwitch(row, col - 1);
            lightSwitch(row - 1, col);
            lightSwitch(row, col + 1);
            lightSwitch(row + 1, col);
            return checkLightsOut(boardLayout) ? 1 : 0;
        }
    }

    /**
     * Checks the switch state of the square and turns it on or off
     * 
     * @param row
     * @param col
     */
    public void lightSwitch (int row, int col)
    {
        if (boardLayout[row][col] == 0)
        {
            boardLayout[row][col] = 1;
        }
        else if (boardLayout[row][col] == 1)
        {
            boardLayout[row][col] = 0;
        }
    }

    /**
     * Checks whether or not all the lights are turned off on the board, if true increments winCount by 1.
     * @param A
     * @return
     */
    public boolean checkLightsOut (int[][] A)
    {
        for (int i = 0; i < A.length; i++)
        {
            for (int j = 0; j < A[i].length; j++)
            {
                /*
                 * for (int[] x : A) { for (int y : x) { System.out.print(y + " "); } System.out.println(); }
                 * System.out.println();
                 */
                if (A[i][j] != 1)
                {
                    return false;
                }
                //System.out.println();
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
            return occupant;
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
