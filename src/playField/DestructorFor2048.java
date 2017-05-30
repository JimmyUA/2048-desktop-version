package playField;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class destroys 2048 tile by setting it a value 1024 after 
 * determinated time period
 * @author Sergey
 * v.6.33.4
 */
public class DestructorFor2048 implements ActionListener{
	private Tile tile;

	// Destructor constructor)
	
	public DestructorFor2048(Tile tile){
		this.tile = tile;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		tile.setValue(1024);
		Toolkit.getDefaultToolkit().beep();
		
	}

}
