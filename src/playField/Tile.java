package playField;


/**
 * This class describes a tile
 * @author Sergey
 * v.6.33.4
 * All rights reserved
 */

public class Tile {
	private String color;
	private int value;
	private int size;
	private int x;                //coordinates
	private int y;                
	
	// Initialization block
	{
		int temp = (int)Math.random();
		if (temp > 0.8){
			value = 4;
		}
		else {
			value = 2;
		}
		
		
	}

	// constructor
	
	public Tile(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Method sets value of current tile
	 * @param x
	 */
	public void setValue(int value){
		this.value = value;
	}
	
	/**
	 * Method sets x coordinate of current tile
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Method sets y coordinate of current tile
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Method returns x coordinate of tile
	 * @return x coordinate
	 */
	public int getXCoord(){
		return x;
	}
	
	/**
	 * Method returns y coordinate of tile
	 * @return x coordinate
	 */
	public int getYCoord(){
		return y;
	}
		
	/**
	 * Method returns current tile value
	 * @return current tile value
	 */
	public int getValue(){
		return value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ""+ value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + size;
		result = prime * result + value;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (value != other.value)
			return false;
		return true;
	}

	

}
