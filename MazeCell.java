/**
 * Represents one cell of a maze. The cell can be a wall, or it can have a crumb, 
 * or neither, but should never both have a crumb and be a wall.
 * @author Sarah Gilliland, for CS367 Spring 2013
 *
 */
public class MazeCell {

	/* Class constants */
	public static final int WALL = 0;
	public static final int START = 1;
	public static final int END = 2;
	public static final int OPEN = 3;
	
	private int type, row, col;
	private boolean hasCrumb;
	private MazeCell prev;  //Used for finding a path - the MazeCell before
							//this cell in the path
	
	/**
	 * Creates a new maze cell of the specified type (WALL, START, END,
	 * or OPEN), in the specified position.
	 * @param type the type of the cell, either WALL, START, END, or OPEN.
         * @param row the row position of the cell
         * @param col the column position of the cell
	 */
	public MazeCell(int type, int row, int col) {
		if (type < 0 || type > 3)
			throw new IllegalArgumentException(type + " is not a " +
					"valid cell type.");
		this.type = type;
		this.row = row;
		this.col = col;
	}
	
	/**
	 * Drops a crumb in this cell (marks it as having been visited). Should 
	 * never be called on a WALL.
	 */
	public void dropCrumb() {
		if (type==MazeCell.WALL)
			throw new IllegalStateException("This operation cannot be" +
					" performed on a WALL.");
		hasCrumb = true;
	}
	
	/**
	 * Returns whether or not this cell has a crumb (is marked as visited).
	 * @return whether this cell has a crumb
	 */
	public boolean hasCrumb() {
		return hasCrumb;
	}
	
	/**
	 * Returns the type of this cell: OPEN, WALL, START or END.
	 * @return the integer type of this cell, one of OPEN, WALL, START, or END.
	 */
	public int type() {
		return type;
	}
	
	/**
	 * Returns the row of this cell.
	 * @return the row of this cell
	 */
	public int row(){
		return row;
	}
	
	/**
	 * Returns the column of this cell.
	 * @return the column of this cell
	 */
	public int col(){
		return col;
	}
	
	/**
	 * Returns the cell that came prior to this one in the path.
	 * @return The cell before this cell in the path
	 */
	public MazeCell getPrev() {
		return prev;
	}
	
	/**
	 * Sets the previous cell to the specified cell.
	 * @param prev the previous MazeCell in the path
	 */
	public void setPrev(MazeCell prev) {
		this.prev = prev;
	}
	
	/**
	 * Returns this cell as a String. The String representation of the type of 
	 * cell is as follows:
	 * Wall = pipe ("|")
	 * Open = space
	 * Start = S
	 * Exit = X
	 * @return The String representation of this cell
	 */
	public String toString() {
		switch (type) {
		case WALL:
			return "|";
		case OPEN:
			return " ";
		case START:
			return "S";
		case END:
			return "X";
		default:
			return "Unknown";
		}
	}
}
