package GUI;

import javax.swing.JTextField;

/**
 * Class holds properties for GUI text fields
 * @author Sergey
 * v.6.33.4
 */
public class TextFields extends JTextField{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TextFields(){
		this.setFont(this.getFont().deriveFont(24f));
		this.setSize(500, 65);
	}
}
