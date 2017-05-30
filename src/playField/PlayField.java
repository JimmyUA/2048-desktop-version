package playField;

import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * This class stores two-dimentional array of tiles represents play field
 * 
 * @author Sergey 2048
 * v.6.33.4
 * 
 */

public class PlayField {
	private Tile[][] tiles;
	private int size;
	private Score score;
	private int delay;

	// play field constructor

	public PlayField(int size, Score score) {
		this.setSize(size);
		this.score = score;
		delay = 500_000;
	}
	
	/**
	 * Method destroys tile with value 2048 after determinated time period, 
	 * destroys means setting value 1024 for it
	 * @param tile
	 */
	public void destroy2048Tile(Tile tile) {
		// construct a timer wich will destroy 2048 tile after 3 minutes
		ActionListener destructor = new DestructorFor2048(tile);
		Timer timer = new Timer(delay, destructor);

		if (tile.getValue() == 2048) {
			timer.start();

		}
	}

	/**
	 * Method checks if there is a possibility to make a step by looking for
	 * the tiles with the same value around every tile
	 * @return true if there are step possibility and false otherwise
	 */
	public boolean isStepsAvailable() {
		for (int i = 0; i < tiles.length - 1; i++) {
			for (int j = 0; j < tiles.length - 1; j++) {

				if (tiles[i][j].getValue() == tiles[i + 1][j].getValue()
						|| tiles[i][j].getValue() == tiles[i ][j + 1].getValue()) {
					return true;
				}
			}
		}
		
		for (int j = 0; j < tiles.length - 1; j++){
			if (tiles[tiles.length - 1][j].getValue() == tiles[tiles.length - 1][j + 1].getValue()
					|| tiles[tiles.length - 1][j].getValue() == tiles[tiles.length - 2][j].getValue()) {
				return true;
			}
		}
		for (int i = 0; i < tiles.length - 1; i++){
			if (tiles[i][tiles.length - 1].getValue() == tiles[i][tiles.length - 2].getValue()
					|| tiles[i][tiles.length - 1].getValue() == tiles[i + 1][tiles.length - 1].getValue()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method initialising array of tiles 
	 */
	public void setTilesArray() {
		tiles = new Tile[size][size];
	}

	/**
	 * Method initialising array of tiles based on array of integers represnts tile values
	 * @param savedArray
	 */
	public void setSavedTilesArray(int[][] savedArray) {
		tiles = new Tile[size][size];
		int notNull = 0;
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles.length; j++) {
				if (savedArray[i][j] != 0) {
					Tile tile = new Tile(i, j);
					tile.setValue(savedArray[i][j]);
					addTile(tile);
					notNull++;
				}
			}
		}
		if (notNull < 3) {
			addTile(new Tile(size - (int) (Math.random() * 2 + 1), (int) (Math.random() * size)));
			addTile(new Tile(size - (int) (Math.random() * 2 + 1), (int) (Math.random() * size)));
		}
	}

	/**
	 * Sets size for current play field instance
	 * 
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Method updates field after tiles moved to the right 
	 * @return true if there were any changes in tiles array and false otherwise
	 */
	public boolean moveToTheRight() {

		boolean changes = false; // tracks if there were changes
		if (connectTilesR()) {
			changes = true;
		}
		for (int i = 0; i < tiles.length; i++) {
			int nulls = 0;
			for (int j = tiles.length - 1; j >= 0; j--) {
				if (isNull(tiles[i][j])) {
					nulls++;
					continue;
				} else {
					if (nulls > 0) {
						tiles[i][j + nulls] = tiles[i][j];
						tiles[i][j] = null;
						j += nulls;// - 1;
						nulls = 0;
						changes = true;
					}
				}
			}
		}

		return changes;
	}

	/**
	 * Connect tiles after move to the right
	 * @return true if tiles were connected and false otherwise
	 */
	private boolean connectTilesR() {
		boolean isConnected = false;
		for (int i = 0; i < tiles.length; i++) {
			Tile firstNotNull = null;
			int x = 0;
			int y = 0;
			for (int j = tiles.length - 1; j >= 0; j--) {

				if (tiles[i][j] != null && isNull(firstNotNull)) {
					firstNotNull = tiles[i][j];
					x = i;
					y = j;
					continue;
				}

				if (!isNull(tiles[i][j]) && !isNull(firstNotNull)) {
					if (/* tiles[i][j].getValue() == firstNotNull.getValue() */
					tiles[i][j].equals(firstNotNull)) {
						tiles[x][y].setValue(tiles[i][j].getValue() * 2);
						score.updateScore(tiles[x][y].getValue());
						tiles[i][j] = null;
						firstNotNull = null;
						isConnected = true;
						destroy2048Tile(tiles[x][y]);
					} else {
						x = i;
						y = j;
						firstNotNull = tiles[i][j];
					}
				}

			}
		}
		return isConnected;
	}

	/**
	 * Method updates field after tiles moved to the left 
	 * @return true if there were any changes in tiles array and false otherwise
	 */
	public boolean moveToTheLeft() {
		boolean changes = false; // tracks if there were changes
		if (connectTilesL()) {
			changes = true;
		}
		for (int i = 0; i < tiles.length; i++) {
			int nulls = 0;
			for (int j = 0; j < tiles.length; j++) {
				if (isNull(tiles[i][j])) {
					nulls++;
					continue;
				} else {
					if (nulls > 0) {
						tiles[i][j - nulls] = tiles[i][j];
						tiles[i][j] = null;
						j -= nulls; // - 1;
						nulls = 0;
						changes = true;
					}
				}
			}
		}
		return changes;
	}

	/**
	 * Connect tiles after move to the left
	 * @return true if tiles were connected and false otherwise
	 */

	private boolean connectTilesL() {
		boolean isConnected = false;
		for (int i = 0; i < tiles.length; i++) {
			Tile firstNotNull = null;
			int x = 0;
			int y = 0;
			for (int j = 0; j < tiles.length; j++) {

				if (tiles[i][j] != null && isNull(firstNotNull)) {
					firstNotNull = tiles[i][j];
					x = i;
					y = j;
					continue;
				}

				if (!isNull(tiles[i][j]) && !isNull(firstNotNull)) {
					if (/* tiles[i][j].getValue() == firstNotNull.getValue() */
					tiles[i][j].equals(firstNotNull)) {
						tiles[x][y].setValue(tiles[i][j].getValue() * 2);
						score.updateScore(tiles[x][y].getValue());
						tiles[i][j] = null;
						firstNotNull = null;
						isConnected = true;
						destroy2048Tile(tiles[x][y]);
					} else {
						x = i;
						y = j;
						firstNotNull = tiles[i][j];
					}
				}

			}
		}
		return isConnected;
	}

	/**
	 * Method updates field after tiles moved bottom 
	 * @return true if there were any changes in tiles array and false otherwise
	 */
	public boolean moveToTheBottom() {
		boolean changes = false; // tracks if there were changes
		if (connectTilesB()) {
			changes = true;
		}
		for (int i = 0; i < tiles.length; i++) {
			int nulls = 0;
			for (int j = tiles.length - 1; j >= 0; j--) {
				if (isNull(tiles[j][i])) {
					nulls++;
					continue;
				} else {
					if (nulls > 0) {
						tiles[j + nulls][i] = tiles[j][i];
						tiles[j][i] = null;
						j += nulls;// - 1;
						nulls = 0;
						changes = true;
					}
				}
			}
		}
		return changes;
	}

	/**
	 * Connect tiles after move bottom
	 * @return true if tiles were connected and false otherwise
	 */
	private boolean connectTilesB() {
		boolean isConnected = false;
		for (int i = 0; i < tiles.length; i++) {
			Tile firstNotNull = null;
			int x = 0;
			int y = 0;
			for (int j = tiles.length - 1; j >= 0; j--) {

				if (tiles[j][i] != null && isNull(firstNotNull)) {
					firstNotNull = tiles[j][i];
					x = j;
					y = i;
					continue;
				}

				if (!isNull(tiles[j][i]) && !isNull(firstNotNull)) {
					if (/* tiles[j][i].getValue() == firstNotNull.getValue() */
					tiles[j][i].equals(firstNotNull)) {
						tiles[x][y].setValue(tiles[j][i].getValue() * 2);
						score.updateScore(tiles[x][y].getValue());
						tiles[j][i] = null;
						firstNotNull = null;
						isConnected = true;
						destroy2048Tile(tiles[x][y]);
					} else {
						x = j;
						y = i;
						firstNotNull = tiles[j][i];
					}
				}

			}
		}
		return isConnected;
	}

	/**
	 * Method updates field after tiles moved up 
	 * @return true if there were any changes in tiles array and false otherwise
	 */
	public boolean moveUp() {
		boolean changes = false; // tracks if there were changes
		if (connectTilesU()) {
			changes = true;
		}
		for (int i = 0; i < tiles.length; i++) {
			int nulls = 0;
			for (int j = 0; j < tiles.length; j++) {
				if (isNull(tiles[j][i])) {
					nulls++;
					continue;
				} else {
					if (nulls > 0) {
						tiles[j - nulls][i] = tiles[j][i];
						tiles[j][i] = null;
						j -= nulls;// - 1;
						nulls = 0;
						changes = true;
					}
				}
			}
		}
		return changes;
	}

	/**
	 * Connect tiles after move up
	 * @return true if tiles were connected and false otherwise
	 */
	private boolean connectTilesU() {
		boolean isConnected = false;
		for (int i = 0; i < tiles.length; i++) {
			Tile firstNotNull = null;
			int x = 0;
			int y = 0;
			for (int j = 0; j < tiles.length; j++) {

				if (tiles[j][i] != null && isNull(firstNotNull)) {
					firstNotNull = tiles[j][i];
					x = j;
					y = i;
					continue;
				}

				if (!isNull(tiles[j][i]) && !isNull(firstNotNull)) {
					if (/* tiles[j][i].getValue() == firstNotNull.getValue() */
					tiles[j][i].equals(firstNotNull)) {
						tiles[x][y].setValue(tiles[j][i].getValue() * 2);
						score.updateScore(tiles[x][y].getValue());
						tiles[j][i] = null;
						firstNotNull = null;
						isConnected = true;
						destroy2048Tile(tiles[x][y]);
					} else {
						x = j;
						y = i;
						firstNotNull = tiles[j][i];
					}
				}

			}
		}
		return isConnected;
	}

	/**
	 * Checks if tile is a null
	 * 
	 * @param tile
	 * @return true is tile is null and false otherwise
	 */
	public boolean isNull(Tile tile) {
		if (tile == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method checks if play field is full
	 * @return true if all tiles are not nulls and false otherwise
	 */
	public boolean isFull() {
		int nulls = 0;
		for (Tile[] line : tiles) {
			for (Tile tile : line) {
				if (tile == null) {
					nulls++;
				}
			}
		}
		if (nulls > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Method adds tile to a field to the empty place randomly
	 * 
	 * @param tile
	 */
	public void addTile(Tile tile) {
		
		boolean isAdded = false;
		if (isNull(tiles[tile.getXCoord()][tile.getYCoord()])) {
			tiles[tile.getXCoord()][tile.getYCoord()] = tile;
			isAdded = true;
		} else {
			for (int i = 0; i < tiles.length; i++) {
				if (isAdded) {
					break;
				}
				for (int j = (int) (Math.random() * tiles.length); j < tiles.length; j++) {
					if (isNull(tiles[i][j])) {
						tiles[i][j] = tile;
						isAdded = true;
						break;
					}
					if (isAdded) {
						break;
					}
				}
			}
		}
	}

	/**
	 * This method prints current instance of paly field
	 */
	@Deprecated
	public void showField() {
		for (Tile[] line : tiles) {
			for (Tile tile : line) {
				if (tile == null) {
					System.out.print("  _  ");
				} else {
					System.out.print("  " + tile.getValue() + "  ");
				}
			}
			System.out.println();
		}
	}

	/**
	 * Method returns tiles array
	 * 
	 * @return tiles array with current play field size
	 */
	public Tile[][] getTilesArray() {
		return tiles;
	}

	/**
	 * Method returns play field size
	 * 
	 * @return size of the play field
	 */
	public int getSize() {
		return size;
	}

	/**
	 * 
	 * @return score refers to current play field
	 */
	public Score getScore() {
		return score;
	}

	/**
	 * Method makes a string represents a values of current tiles array 
	 * starts from tile with indexes [0][0] and ends with tile with indexes [array size - 1][array size - 1]
	 * @return string represents a values of current tiles array,
	 * for example if tiles array is :
	 * 		[[0, 0, 0, 0],
	 * 		 [0, 0, 0, 0],
	 * 		 [0, 0, 0, 0],
	 * 		 [0, 0, 0, 0]]
	 * 
	 * String will have next look "0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0"
	 */
	public String getPlayFieldString() {
		String field = "";
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles.length; j++) {
				if (isNull(tiles[i][j])) {
					field += ", 0";
				} else {
					field += ", " + tiles[i][j];
				}

			}
		}
		field = field.substring(2);
		return field;
	}

}
