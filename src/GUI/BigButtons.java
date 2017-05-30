package GUI;

/**
 * Class holds properties for GUI buttons
 * @author Sergey
 * v.6.33.4
 */
public class BigButtons extends Buttons{
	
	
	private static final long serialVersionUID = 1L;

	public BigButtons(String text){
		super.setText(text);
		this.setSize(200, 60);
		this.setFont(this.getFont().deriveFont(24f));
	}

}
