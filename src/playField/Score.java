package playField;


/**
 * This class holds current game score.
 * @author Sergey
 * v.6.33.4
 * All rights reserved
 */


public class Score {
	private int score;
	
	{
		score = 0;
	}

	/**
	 * This method set current game score
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	/**
	 * This method increases score on some amount of points
	 * depends on produced tile value
	 * @param points
	 */
	public void updateScore(int points) {
		this.score += points;
	}
	
	/**
	 * This method returns current game score
	 * @return game score
	 */
	public int getScore() {
		return score;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "" + score;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + score;
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
		Score other = (Score) obj;
		if (score != other.score)
			return false;
		return true;
	}
	

	
}
