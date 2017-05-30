package GUI;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

/**
 * Class holds properties for GUI panels and sets key listeners
 * @author Sergey
 * v.6.33.4
 */
public class Panel extends JPanel implements KeyListener{

	
	private static final long serialVersionUID = 1L;
	private GUI2048 gui;
	
	public Panel(GUI2048 gui){
		this.gui = gui;
		setFocusable(true);
		addKeyListener(this);
		addFocusListener( new FocusListener() {
	       
			@Override
			public void focusGained(FocusEvent arg0) {
				repaint();
				
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				 repaint();
				
			}
	    });


	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            gui.moveRight();
            
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        	gui.moveLeft();
        	
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            gui.moveUp();
            
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        	gui.moveDown();
        	
        }
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
