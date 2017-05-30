package GUI;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import user.User2048;

/**
 * Class represents tile like a button
 * @author Sergey
 * v.6.33.4
 */
public class PlayFieldButton extends JButton{
	
	private static final long serialVersionUID = 1L;
	private User2048 user;

	public PlayFieldButton(String text, User2048 user){
		super.setText(text);
		this.user = user;
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		this.setForeground(Color.WHITE);
		this.setFont(this.getFont().deriveFont(18f));
		this.setEnabled(false);
		
	}
	
	public PlayFieldButton(User2048 user){
		this.user = user;
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		this.setFont(this.getFont().deriveFont(18f));
		this.setForeground(Color.WHITE);
		this.setEnabled(false);
		setSize();
		
	}
	
	// no arguments constructor
	public PlayFieldButton(){
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		this.setFont(this.getFont().deriveFont(18f));
		this.setForeground(Color.WHITE);
		this.setEnabled(false);
		setSize();
	}
	
	/**
	 * Method sets button size depending on play fiel size
	 */
	private void setSize(){
		switch (user.getField().getSize()) {
		case 4:
			this.setSize(120, 120);
			break;

		case 5:
			this.setSize(96, 96);
			break;

		case 6:
			this.setSize(80, 80);
			break;

		case 8:
			this.setSize(60, 60);
			break;

		default:
			break;
		}
	}
}
