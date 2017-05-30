package GUI;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * Class holds properties for GUI labels
 * @author Sergey
 * v.6.33.4
 */
public class Labels extends JLabel{


	private static final long serialVersionUID = 1L;

	public Labels(){
		this.setForeground(Color.WHITE);
	}
	
	public Labels(String text){
		super.setText(text);
		this.setForeground(Color.WHITE);
	}
}
