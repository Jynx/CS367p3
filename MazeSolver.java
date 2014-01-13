import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Class used to solve mazes
 * @author Steven Volocyk
 *
 */
public class MazeSolver {
    MazeCell mStart = null; //Start cell for maze
    //various variables for the Row/Col#'s
    int mRowCount = 0;
    int mColCount = 0;
    int mRows = 0;
    int mRow = 0;
    int mCol = 0;
    MazeCell[][] mMaze = null;      // initialized maze
    MazeCell mCurrentCell = null;   // temp variable for cell being worked on
    MazeCell mCellUp = null;        // North position for solving
    MazeCell mCellDown = null;      // South position for solving
    MazeCell mCellLeft = null;      // West position for solving
    MazeCell mCellRight = null;     // East position for solving
    ArrayQueue<MazeCell> mMazeQueue = new ArrayQueue<MazeCell>();
	    
    //Stack for storing solution path
    Stack<MazeCell> mMazeSolution = new Stack<MazeCell>(); 
	    
    boolean mExitFound = false;     //exit found for solution path
    boolean mNoSolution = false;    // boolean for if solution found
    
    public static void main(String[] args) {
        MazeSolver solver = new MazeSolver();
        Scanner stdin = new Scanner(System.in);
        boolean done = false;
        while (!done) {
            System.out.print("Enter option - d, m, p, s, or x: ");
            String input = stdin.nextLine();
            
            if (input.length() > 0) {
                char choice = input.charAt(0);  // strip off option character
                String remainder = "";  // used to hold the remainder of input
                // trim off any leading or trailing spaces
                remainder = input.substring(1).trim();
                
                switch (choice) {
                
                case 'd':
                    solver.printMaze();
                    break;
                
                case 'm':
                    solver.setMazeCommand(remainder); 
                    break;
                    
                case 'p':
                    solver.printSolution();
                    break;
                
                case 's':
                    solver.solveMaze();
                    break;  
                case 'x':
                    System.out.println("Exit");
                    done = true;
                    break;
                        
                default:
                    System.out.println("Unknown command.");
                    break;    
                }
            }
        }
        stdin.close();
    }

	    
    /**
     * Prints the maze if one has been loaded
     */
    void printMaze() {
        if (mMaze == null) {
            System.out.println("No maze specified.");
            return;
        }
        // loop for printing maze cell by cell
        for (int i = 0; i < mRowCount; i++) {
            for (int j = 0; j < mColCount; j++) {
                System.out.print(mMaze[i][j]);
            }
            System.out.print("\n");
        }
    }
    
    /**
     * Loads the maze in the given file
     * @param fileName The name of the file to load
     */
    void setMazeCommand(String fileName) {
        mMaze = null;
        mMaze = setMaze(fileName);
        mMazeQueue.clear();
        mExitFound = false;
        mNoSolution = false;
        mMazeSolution.clear();
        if (mMaze != null) { 
            System.out.println("Maze Stored");
        } 
    }
    
    void printSolution() {
        if (mMaze == null) { 
            System.out.println("No Maze Specified.");
            return;
         } 
         if (mNoSolution) {
             System.out.println("no Solution");
             return;
         }
         if (!mExitFound) {
             System.out.println("Maze has not yet been solved");
             return;
         }
         // loop for printing maze solution path
         while (!mMazeSolution.isEmpty()) {
             MazeCell nextCell = mMazeSolution.pop();
             mRow = nextCell.row();
             mCol = nextCell.col();
             System.out.println("("+ mRow +"," + mCol +")");
         }
    }
    
    /**
     * Solves the previously loaded maze
     */
    void solveMaze() {
        if (mMaze == null) {
            System.out.println("No maze specified.");
            return;
        }
        
        if (mExitFound) {
            System.out.println("Solution found!");
            return;
        } else if (mNoSolution) {
            System.out.println("No solution");
            return;
        }
        
        mExitFound = false;                  
        mMazeQueue.enqueue(mStart);
        while (!mMazeQueue.isEmpty()) {
            mCurrentCell = mMazeQueue.dequeue();
            mRow = mCurrentCell.row();
            mCol = mCurrentCell.col();
            // skips to next cell if current cell has crumb
            if (!mCurrentCell.hasCrumb()) {
                //checks for cell type, drops crumb if not found
                if (!mCurrentCell.hasCrumb() && 
                  !(mCurrentCell.type() == MazeCell.WALL) ) {
                    mCurrentCell.dropCrumb();
                }
                // examines cell north of currentCell
                // built in safe programming for out of bounds excep
                // checks for wall, sets prev, enqueues if no crumb
                // and not of type WALL.
                if (mRow != 0) {
                    mCellUp = mMaze[mRow - 1][mCol];
                    if (!mCellUp.hasCrumb() &&
                        !(mCellUp.type() == MazeCell.WALL) ) {                                   
                        mCellUp.setPrev(mCurrentCell);
                        mMazeQueue.enqueue(mCellUp);
                    }
                }
                // examines cell east of currentCell
                // built in safe programming for out of bounds excep
                // checks for wall, sets prev, enqueues if no crumb
                // and not of type WALL.
                if (mCol != mColCount -1) {
                    mCellRight = mMaze[mRow][mCol + 1]; 
                    if (!mCellRight.hasCrumb() && 
                        !(mCellRight.type() == MazeCell.WALL) ) {
                        mCellRight.setPrev(mCurrentCell);
                        mMazeQueue.enqueue(mCellRight);
                    }
                }
                // examines cell south of currentCell
                // built in safe programming for out of bounds excep
                // checks for wall, sets prev, enqueues if no crumb
                // and not of type WALL.
                if (mRow != mRowCount - 1) {
                    mCellDown = mMaze[mRow + 1][mCol];
                    if (!mCellDown.hasCrumb() && 
                        !(mCellDown.type() == MazeCell.WALL) ) {                             
                        mCellDown.setPrev(mCurrentCell);
                        mMazeQueue.enqueue(mCellDown);
                    }
                }
                // examines cell west of currentCell
                // built in safe programming for out of bounds excep
                // checks for wall, sets prev, enqueues if no crumb
                // and not of type WALL.
                if (mCol != 0) {
                    mCellLeft = mMaze[mRow][mCol - 1];  
                    if (!mCellLeft.hasCrumb() && 
                        !(mCellLeft.type() == MazeCell.WALL) ) {                                 
                        mCellLeft.setPrev(mCurrentCell);
                        mMazeQueue.enqueue(mCellLeft);
                    }
                }
            }
            if (mCurrentCell.type() == MazeCell.END) {
                mExitFound = true;
                System.out.println("Solution found!");
                boolean finished = false;
                // creates solution path stored with a stack
                while(!finished) {
                    // obtains previous chained cell pushes
                    // cell into stack, and reassigns currentCell
                    MazeCell nextCell = mCurrentCell.getPrev();
                    mMazeSolution.push(mCurrentCell);
                    mCurrentCell = nextCell;
                    // ends loop once START cell is found
                    if(nextCell.type() == MazeCell.START) {
                        mMazeSolution.push(nextCell);//pushes final cell
                        finished = true;
                    }
                }
                break;
            }               
        }
        if (!mExitFound){
            System.out.println("No solution");
            mNoSolution = true;
        }   
    }
    
	/**
	 * Reads a maze from a file and stores the maze
	 * as a 2d array within program for manipulation.
	 * @param fileName
	 * @return maze Stored 2d array read from file
	 */
	private MazeCell[][] setMaze(String fileName) {
	    MazeCell[][] maze = null;
	    Scanner fileIn = null;
	    mRows = 0;
		try {
			if (!fileName.endsWith(".txt")) {
				fileName = fileName + ".txt";
			}
			// creates scanner for file
			fileIn = new Scanner(new File(fileName));
			// used for marking first line of file for obtaining
			// only # of rows and colums
			boolean firstLine = true; 
			do {
				if (firstLine == true) {
					mRowCount = fileIn.nextInt(); // # of rows
					mColCount = fileIn.nextInt();// # of columns
					//creates new 2d maze based off of rows/columns above
					maze = new MazeCell[mRowCount][mColCount];
					firstLine = false;
					fileIn.nextLine(); //advances to start of maze in file
				}
				if (!firstLine) {
					int type = 0;
					String line = fileIn.nextLine();
					char[] chars = line.toCharArray();
					//switch for interpreting cell type.
					for (int x = 0; x < chars.length; x++) {
						char c = chars[x];
						switch (c) {
						case '|':
							type = 0;
							break;
						case ' ':
							type = 3;
							break;
						case 'S':
							type = 1;
							break;
						case 'X':
							type = 2;
							break;
						}
						//creates new maze with given parameters
						//for constructor
						MazeCell cell = new MazeCell(type, mRows, x); 
						maze[mRows][x] = cell;
						if(type == 1) {
							mStart = cell;//stores start cell when found
						}
					}
					mRows++;
				}
			} while (fileIn.hasNextLine());
		} catch (FileNotFoundException ex) {
			System.out.println("Error: Cannot access input file.");
		} finally {
    		if (fileIn != null) {
    		    fileIn.close();
    		}
		}
		return maze;
	}
	
}
