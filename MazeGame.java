package solution;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * A maze game.
 * 
 * @author 1/29/2018
 * @version 1
 *
 */
public class MazeGame
{
    /**
     * The size of each side of the game map.
     */
    private final static int HEIGHT = 19;
    private final static int WIDTH = 39;

    /**
     * The game map, as a 2D array of ints.
     */
    private boolean[][] blocked;
    private boolean[][] bread = new boolean[HEIGHT][WIDTH];

    /**
     * The current location of the player vertically.
     */
    // TODO: add field here.
    private int userCol;

    /**
     * The current location of the player horizontally.
     */
    // TODO: add field here.
    private int userRow;

    /**
     * The scanner from which each move is read.
     */
    // TODO: add field here.
    private Scanner moveScanner;

    /**
     * The row and column of the goal.
     */
    // TODO: add fields here.
    private int goalRow;
    private int goalCol;

    /**
     * The row and column of the start.
     */
    // TODO: add fields here.
    private int startRow;
    private int startCol;

    /**
     * Constructor initializes the maze with the data in 'mazeFile'.
     * @param mazeFile the input file for the maze
     */
    public MazeGame(String mazeFile)
    {
        // TODO
        loadMaze(mazeFile);
        moveScanner = new Scanner(System.in);

    }

    /**
     * Constructor initializes the maze with the 'mazeFile' and the move 
     * scanner with 'moveScanner'.
     * @param mazeFile the input file for the maze
     * @param moveScanner the scanner object from which to read user moves
     */
    public MazeGame(String mazeFile, Scanner moveScanner)
    {
        // TODO
        loadMaze(mazeFile);
        this.moveScanner = moveScanner;
    }

    /**
     * getMaze returns a copy of the current maze for testing purposes.
     * 
     * @return the grid
     */
    public boolean[][] getMaze()
    {
        if (blocked == null)
        {
            return null;
        }
        boolean[][] copy = new boolean[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++)
        {
            for (int j = 0; j < WIDTH; j++)
            {
                copy[i][j] = blocked[i][j];
            }
        }
        return copy;
    }

    /**
     * getUserCol returns a copy of the user's vertical position.
     * @return userCol
     */
    public int getUserCol()
    {
        return userCol;
    }

    /**
     * getUserRow returns the horizontal position.
     * @return userRow
     */
    public int getUserRow()
    {
        return userRow;
    }

    /**
     * getMoveScanner.
     * @return moveScanner
     */
    public Scanner getMoveScanner()
    {
        return moveScanner;
    }

    /**
     * setMaze sets the current map for testing purposes.
     * 
     * @param maze
     *            another maze.
     */
    public void setMaze(boolean[][] maze)
    {
        this.blocked = maze;
    }

    /**
     * setUserCol sets user's vertical position.
     * @param userCol 
     */
    public void setUserCol(int userCol)
    {
        this.userCol = userCol;
    }

    /**
     * serUserRow sets user's horizontal position.
     * @param userRow 
     */
    public void setUserRow(int userRow)
    {
        this.userRow = userRow;
    }

    /**
     * setMoveScanner .
     * @param moveScanner 
     */
    public void setMoveScanner(Scanner moveScanner)
    {
        this.moveScanner = moveScanner;
    }

    /**
     * Function loads the data from the maze file and creates the 'blocked' 
     * 2D array.
     *  
     * @param mazeFile the input maze file.
     */
    public void loadMaze(String mazeFile)
    {
        try
        {          
            blocked = new boolean[HEIGHT][WIDTH];
            File file = new File(mazeFile);
            Scanner reader = new Scanner(file);

            for (int i = 0; i < HEIGHT; i++)
            {
                for (int j = 0; j < WIDTH; j++)
                {
                    String pos = reader.next();

                    if (pos.equals("S") || pos.equals("s"))
                    {
                        blocked[i][j] = false;
                        startRow = i;
                        startCol = j;
                        userRow = i;
                        userCol = j;
                    }
                    else if (pos.equals("G") || pos.equals("g"))
                    {
                        blocked[i][j] = false;
                        goalRow = i;
                        goalCol = j;
                    }
                    else if (pos.equals("1"))
                    {
                        blocked[i][j] = true;
                    }
                    else if (pos.equals("0"))
                    {
                        blocked[i][j] = false; 
                    }
                }
            }
            reader.close();
        }
        catch (FileNotFoundException fnf)
        {
            System.out.println("The file does not exist.");
        }

    }

    /**
     * Actually plays the game.
     */
    public void playGame()
    {

        moveScanner = new Scanner(System.in);
        printMaze();
        String move = "";

        System.out.print("Enter Up, Down, Right, Left, or Quit:");
        do
        {
            move = moveScanner.nextLine();
            makeMove(move);
            printMaze();
            System.out.print("Enter Up, Down, Right, Left, or Quit:");
        } while (move != "q" && !playerAtGoal());
    }

    /**
     * Checks to see if the player has won the game.
     * @return true if the player has won.
     */
    public boolean playerAtGoal()
    {
        return userRow == goalRow && userCol == goalCol;
    }

    /**
     * Makes a move based on the String.
     * 
     * @param move
     *            the direction to make a move in.
     * @return whether the move was valid.
     */
    public boolean makeMove(String move)
    {
        char dir = move.charAt(0);
        if (dir == 'u' || dir == 'U')
        {
            if (userRow < 0 || blocked[userRow - 1][userCol])
            {
                return false;
            }
            else
            {                
                bread[userRow][userCol] = true;
                userRow -= 1;
                return true;
            }
        }
        else if (dir == 'd' || dir == 'D')
        {
            if (userRow >= (HEIGHT - 1) || blocked[userRow + 1][userCol])
            {
                return false;
            }
            else
            { 
                bread[userRow][userCol] = true;
                userRow += 1;
                return true;
            }
        }
        else if (dir == 'r' || dir == 'R')
        {
            if (userCol < WIDTH && (userCol > (WIDTH - 1) 
                || blocked[userRow][userCol + 1]))
            {
                return false;
            }
            else
            { 
                bread[userRow][userCol] = true;
                userCol += 1;
                return true;
            }
        }
        else if (dir == 'l' || dir == 'L')
        {
            if (userCol < 1 || blocked[userRow][userCol - 1])
            {
                return false;
            }
            else
            { 
                bread[userRow][userCol] = true;
                userCol -= 1;
                return true;
            }
        }
        else if (dir == 'q' || dir == 'Q')
        {
            return true;
        }
        return false;
    }

    /**
     * Prints the map of the maze.
     */
    public void printMaze()
    {
        // TODO



        System.out.print("*");
        int x = 0;
        while (x < WIDTH)
        {
            System.out.print("-");
            x++;
        }
        System.out.print("*");
        System.out.println("");

        for (int i = 0; i < blocked.length; i++)
        {
            System.out.print("|");


            for (int j = 0; j < blocked[i].length; j++)
            {

                if (blocked[i][j] == true)
                {
                    System.out.print("X");
                }
                else if (userRow == i && userCol == j)
                {
                    System.out.print("@");
                }
                //else if (blocked[i][j] == false 
                // && (startRow != i || startCol != j) && 
                // (goalRow != i || goalCol != j))
                //System.out.print(" ");
                else if ((i == startRow && j == startCol) 
                    && (userRow != i || userCol != j))
                {
                    System.out.print("S");
                }
                else if ((goalRow == i && goalCol == j) 
                    && (userRow != i || userCol != j))
                {
                    System.out.print("G");
                }
                else if (bread[i][j])
                {
                    System.out.print(".");
                }
                else
                {
                    System.out.print(" ");
                }

                //
            }
            System.out.println("|");
        }
        System.out.print("*");
        int y = 0;
        while (y < WIDTH)
        {
            System.out.print("-");
            y++;
        }
        System.out.print("*");
        System.out.println("");

        /*
        for(int a = 0; a < bread.length; a++)
            for(int b = 0; b < bread[a].length; b++)
                if ((a != startRow && b != startCol) 
                && (a != goalRow && b != goalCol))
                    System.out.print(bread[a][b]);
         */
    }


    /**
     * Creates a new game, using a command line argument file name, if one is
     * provided.
     * 
     * @param args the command line arguments
     */

    public static void main(String[] args)
    {
        String mapFile = "data/easy.txt";
        Scanner scan = new Scanner(System.in);
        MazeGame game = new MazeGame(mapFile, scan);
        game.playGame();
    }
}
