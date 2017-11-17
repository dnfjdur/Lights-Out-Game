package lightsOut;

import javax.swing.*;
import static lightsOut.LightsOutView.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class LightsOutView extends JFrame implements ActionListener
{
    private final static int WIDTH = 600;
    private final static int HEIGHT = 700;
    public final static int ROWS = 5;
    public final static int COLS = 5;
    public final static Color BACKGROUND_COLOR = Color.GRAY;
    public final static Color ON = Color.YELLOW;
    public final static Color OFF = Color.BLACK;
    public final static Color BOARD_COLOR = Color.WHITE;
    public final static int BORDER = 5;
    public final static int FONT_SIZE = 18;

    /** The "brains" of the game **/
    private LightsOutModel model;

    /** The number of wins there have been **/
    private JLabel wins;

    /** The part of GUI that contains the playing surface **/
    private Board board;

    /** Used to switch between manual and game mode **/
    static boolean mode;

    /**
     * Creates and initializes the game.
     */
    public LightsOutView ()
    {
        // Set the title
        setTitle("CS 1410 Lights Out");

        // Cause the application to end when the windows is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Give the window its initial dimensions
        setSize(WIDTH, HEIGHT);

        // The root panel contains everything
        JPanel root = new JPanel();
        root.setLayout(new BorderLayout());
        setContentPane(root);

        // The center portion contains the playing board
        model = new LightsOutModel(ROWS, COLS);
        board = new Board(model, this);
        root.add(board, "Center");

        // The top portion contains the score
        JPanel winCount = new JPanel();
        winCount.setLayout(new BorderLayout());
        root.add(winCount, "North");

        // Score board
        JPanel scoreBoard = new JPanel();
        // tiePanel.setBackground(BACKGROUND_COLOR);
        wins = new JLabel("Wins: 0");
        wins.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        scoreBoard.add(wins);
        winCount.add(scoreBoard, "Center");

        // The bottom portion contains the New Game and Manual mode buttons
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());

        // New game and manual mode buttons
        JButton newGame = new JButton("New Game");
        JButton manual = new JButton("Enter Manual Mode");
        model.newGame();
        
        newGame.addActionListener(new ActionListener()
        {
            /**
             * Called when the New Game button is clicked. Tells the model to begin a new game, then refreshes the
             * display.
             */
            @Override
            public void actionPerformed (ActionEvent e)
            {
                mode = false;
                model.newGame();
                manual.setText("Enter Manual Mode");
                board.refresh();
            }
        });
        // newGame.setForeground(TIE_COLOR);
        // newGame.setBackground(BACKGROUND_COLOR);
        manual.addActionListener(new ActionListener()
        {
            /**
             * Called when the Enter manual button is clicked. Lets user setup and change their own board one tile at a
             * time.
             */
            @Override
            public void actionPerformed (ActionEvent e)
            {
                if (manual.getText().equals("Enter Manual Mode"))
                {
                    manual.setText("Exit Manual Mode");
                    mode = true;
                    board.refresh();
                }
                else
                {
                    manual.setText("Enter Manual Mode");
                    mode = false;
                    board.refresh();
                }
            }
        });
        newGame.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        manual.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        buttons.add(newGame);  
        buttons.add(manual);
        root.add(buttons, "South");

        // Refresh the display
        board.refresh();
        setVisible(true);
    }

    /**
     * Sets the label that displays the tie count
     */
    public void setWins (int n)
    {
        wins.setText("Wins: " + n);
    }

    @Override
    public void actionPerformed (ActionEvent e)
    {

    }
}

/**
 * The playing surface.
 */
@SuppressWarnings("serial")
class Board extends JPanel implements MouseListener
{

    /** The "brains" of the game */
    private LightsOutModel model;

    /** The top level GUI for the game */
    private LightsOutView display;

    /**
     * Creates the board portion of the GUI.
     */
    public Board (LightsOutModel model, LightsOutView display)
    {
        // Record the model and the top-level display
        this.model = model;
        this.display = display;

        // Set the used to create border effect
        setBackground(BOARD_COLOR);
        setLayout(new GridLayout(ROWS, COLS));

        // Sets up the grid of squares that make up the game.
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                Square s = new Square(i, j);
                s.addMouseListener(this);
                add(s);
            }
        }
    }

    /**
     * Refreshes the display. Called whenever something changes in the model.
     */
    public void refresh ()
    {
        // Iterate through all of the squares that make up the grid
        Component[] squares = getComponents();
        for (Component c : squares)
        {
            Square s = (Square) c;

            int status = model.getOccupant(s.getRow(), s.getCol());
            if (status == 0)
            {
                s.setColor(ON); // "Turns light on"
            }
            else if (status == 1)// "Turns light off"
            {
                s.setColor(OFF);
            }
        }

        // Update the win counts
        display.setWins(model.getWins());

        // Ask that this board be repainted
        repaint();
    }

    @Override
    public void mouseClicked (MouseEvent e)
    {
    }

    /**
     * Called whenever a Square is clicked. Notifies the model that a move has been attempted.
     */
    @Override
    public void mousePressed (MouseEvent e)
    {
        // Check if manual mode is true
        if (!mode)
        {
            // Gets info of the square that was clicked
            Square s = (Square) e.getSource();
            int result = model.move(s.getRow(), s.getCol());
            refresh();
            if (result == 1)
            {
                JOptionPane.showMessageDialog(this, "You win!");
            }
        }
        else
        {
            // Enters manual mode, instead of using the move method that obeys the rules of the game, directly uses the
            // lightSwitch() method so user can setup each tile themselves
            Square s = (Square) e.getSource();
            model.lightSwitch(s.getRow(), s.getCol());
            refresh();
        }
    }

    @Override
    public void mouseReleased (MouseEvent e)
    {
    }

    @Override
    public void mouseEntered (MouseEvent e)
    {
    }

    @Override
    public void mouseExited (MouseEvent e)
    {
    }
}

/**
 * A single square on the board where a move can be made
 */
@SuppressWarnings("serial")
class Square extends JPanel
{
    /** The row within the board of this Square. Rows are numbered from zero; row zero is at the bottom of the board. */
    private int row;

    /** The column within the board of this Square. Columns are numbered from zero; column zero is at the left */
    private int col;

    /** The current Color of this Square */
    private Color color;

    private Square square;

    /**
     * Creates a square and records its row and column
     */
    public Square (int row, int col)
    {
        this.row = row;
        this.col = col;
        this.color = BACKGROUND_COLOR;
    }

    /**
     * Returns the row of this Square
     */
    public int getRow ()
    {
        return row;
    }

    /**
     * Returns the column of this Square
     */
    public int getCol ()
    {
        return col;
    }

    public Square getSquare (int row, int col)
    {
        return square;
    }

    /**
     * Sets the color of this square
     */
    public void setColor (Color color)
    {
        this.color = color;
    }

    /**
     * Paints this Square onto g
     */
    @Override
    public void paintComponent (Graphics g)
    {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(BOARD_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(color);
        // Fills the square slightly smaller for border effect
        g.fillRect(2, 2, getWidth() - 2, getHeight() - 2);
    }
}