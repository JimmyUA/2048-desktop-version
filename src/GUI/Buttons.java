package GUI;

import java.awt.Color;

import javax.swing.JButton;

/**
 * Class holds properties for GUI buttons
 * @author Sergey
 * v.6.33.4
 */
public class Buttons extends JButton{

	
	private static final long serialVersionUID = 1L;

	public Buttons(){
		this.setBackground(Color.BLACK);
		this.setForeground(Color.WHITE);
	}
	
	public Buttons(String text){
		super.setText(text);
		this.setBackground(Color.BLACK);
		this.setForeground(Color.WHITE);
	}
}
