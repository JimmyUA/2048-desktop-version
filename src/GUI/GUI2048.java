package GUI;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import playField.PlayField;
import playField.Tile;
import user.User2048;
import dataBase.DBWorker;

/**
 * Class for 2048 user interfase desktop realization emplements main game process
 * 
 * @author Sergey 
 * v.6.33.4
 */

public class GUI2048 extends JFrame {

	
	private static final long serialVersionUID = 1L;

	private Logger logger;
	private FileHandler handler;
	private User2048 user;
	private PlayField playField;

	private JButton[][] tileButtons;

	private JLabel playFieldLabel;
	private JLabel score;
	private JLabel scoreTitle;
	private JLabel record;
	private JLabel recordTitle;
	private JLabel gameOver;

	private Panel playFieldPanel;
	private Buttons back;

	private Color color2;
	private Color color4;
	private Color color8;
	private Color color16;
	private Color color32;
	private Color color64;
	private Color color128;
	private Color color256;
	private Color color512;
	private Color color1024;
	private Color color2048;
	private Color color4096;
	// private Color colorAll;
	private Color colorDefault;

	// GUI initalization field

	{
		playFieldLabel = new Labels("2048");
		playFieldLabel.setFont(playFieldLabel.getFont().deriveFont(64f));

		gameOver = new Labels("Game over!");
		gameOver.setFont(playFieldLabel.getFont().deriveFont(64f));

		score = new Labels("0");
		score.setFont(score.getFont().deriveFont(20f));

		scoreTitle = new Labels("SCORE");
		scoreTitle.setFont(score.getFont().deriveFont(26f));

		record = new Labels();
		record.setFont(score.getFont().deriveFont(20f));

		recordTitle = new Labels("RECORD");
		recordTitle.setFont(score.getFont().deriveFont(26f));

		back = new Buttons("2048");
		back.setFont(back.getFont().deriveFont(64f));
		
		logger = Logger.getLogger(GUI2048.class.getName());
		try {
			handler = new FileHandler("log.txt");
			logger.addHandler(handler);
		} catch (SecurityException e) {
			logger.log(Level.SEVERE, "Can't create file due to security settings");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Can't create file due to IO problems");
		}

	}

	// GUI constructor

	public GUI2048(User2048 user) {
		super("2048 v. 6.33.4");

		this.user = user;
		playField = user.getField();
		
		// initializing buttons array
		tileButtons = new JButton[playField.getSize()][playField.getSize()];

		// initialyzing tile buttons
		Tile[][] tiles = playField.getTilesArray();

		for (int i = 0; i < tileButtons.length; i++) {
			for (int j = 0; j < tileButtons.length; j++) {

				if (playField.isNull(tiles[i][j])) {
					tileButtons[i][j] = new PlayFieldButton(user);
					setButtonSize(tileButtons[i][j]);
				} else {
					tileButtons[i][j] = new PlayFieldButton("" + tiles[i][j].getValue(), user);
					setButtonSize(tileButtons[i][j]);
				}
			}
			// Colors initialization

			color2 = new Color(0, 0, 0);
			color4 = new Color(47, 79, 79);
			color8 = new Color(46, 139, 87);
			color16 = new Color(0, 100, 0);
			color32 = new Color(85, 107, 47);
			color64 = new Color(139, 0, 0);
			color128 = new Color(220, 20, 60);
			color256 = new Color(199, 21, 133);
			color512 = new Color(255, 105, 180);
			color1024 = new Color(255, 99, 71);
			color2048 = new Color(255, 215, 0);
			color4096 = new Color(255, 239, 213);
			// Color colorAll = new Color(0, 0, 0);
			colorDefault = new Color(220, 220, 220);
		}
		changeButtonsColor();

		Image img = new ImageIcon("2048.jpg").getImage();
		setIconImage(img);
		setSize(850, 850);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocation(500, 0);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				try {
					onExit();
				} catch (InstantiationException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
					e.printStackTrace();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
					e.printStackTrace();
				}
			}
		});

		updateTileButtons();
		playFieldPanel = getPlayFieldPanel();
		setContentPane(playFieldPanel);

		initListeners();
	}

	/**
	 * Method saves current play field and user indatabase and than
	 * terminates application
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws FileNotFoundException
	 */
	private void onExit() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException,
			FileNotFoundException {

		DBWorker worker = new DBWorker();
		if (!playField.isFull()) {
			worker.savePlayField(user);
		}
		worker.saveRecord(user);
		worker.saveLastUser(user);
		System.exit(0);
	}

	/**
	 * Method sets buttons size depending on play field size
	 * @param button
	 */
	private void setButtonSize(JButton button) {
		switch (playField.getSize()) {
		case 4:
			button.setSize(120, 120);
			break;

		case 5:
			button.setSize(96, 96);
			break;

		case 6:
			button.setSize(80, 80);
			break;

		case 8:
			button.setSize(60, 60);
			break;

		default:
			break;
		}
	}

	/**
	 * Method sets listeners for all buttons on a frame
	 */
	private void initListeners() {

		back.addActionListener(event -> {

			try {
				new FirstWindow();
				DBWorker worker = new DBWorker();
				if (!playField.isFull()){
					worker.savePlayField(user);
				}
				worker.saveRecord(user);
				worker.saveLastUser(user);

			} catch (InstantiationException e) {
				logger.log(Level.SEVERE, "Exception: ", e);
			} catch (IllegalAccessException e) {
				logger.log(Level.SEVERE, "Exception: ", e);
			} catch (ClassNotFoundException e) {
				logger.log(Level.SEVERE, "Exception: ", e);
			} catch (FileNotFoundException e) {
				logger.log(Level.SEVERE, "Exception: ", e);
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "Exception: ", e);
			}

			setVisible(false);
		});

	}

	/**
	 * Method returns play field panel
	 * 
	 * @return play field panel
	 */
	private Panel getPlayFieldPanel() {
		playFieldPanel = new Panel(this);

		playFieldPanel.setLayout(null);
		playFieldPanel.setBackground(Color.BLACK);

		score.setText("" + playField.getScore().getScore());

		if (user.getRecord().getRecord() > playField.getScore().getScore()) {
			record.setText("" + user.getRecord().getRecord());
		} else {
			record.setText("" + playField.getScore().getScore());
		}

		// set score elements

		scoreTitle.setSize(100, 30);
		scoreTitle.setLocation(700, 20);

		score.setSize(100, 40);
		record.setLocation(750, 50);

		recordTitle.setSize(150, 30);
		recordTitle.setLocation(20, 20);

		record.setSize(100, 40);
		record.setLocation(50, 50);

		if (playField.getScore().getScore() < 10) {
			score.setLocation(740, 50);
		} else if (playField.getScore().getScore() < 100) {
			score.setLocation(735, 50);
		} else if (playField.getScore().getScore() < 1000) {
			score.setLocation(730, 50);
		} else if (playField.getScore().getScore() < 10000) {
			score.setLocation(725, 50);
		} else if (playField.getScore().getScore() < 100000) {
			score.setLocation(720, 50);
		}

		playFieldLabel.setSize(200, 65);
		playFieldLabel.setLocation(360, 40);

		gameOver.setSize(400, 60);
		gameOver.setLocation(250, 700);

		back.setBorder(null);
		back.setSize(200, 65);
		back.setLocation(340, 40);

		// adding elements to a frame

		playFieldPanel.add(back);
		playFieldPanel.add(scoreTitle);
		playFieldPanel.add(score);
		playFieldPanel.add(recordTitle);
		playFieldPanel.add(record);

		addTileButtons(playFieldPanel);

		return playFieldPanel;
	}

	/**
	 * Method updates play field panel
	 */
	private void updatePlayFieldPanel() {
		score.setText("" + playField.getScore().getScore());

		if (user.getRecord().getRecord() > playField.getScore().getScore()) {
			record.setText("" + user.getRecord().getRecord());
		} else {
			record.setText("" + playField.getScore().getScore());
		}

		if (playField.getScore().getScore() < 10) {
			score.setLocation(740, 50);
		} else if (playField.getScore().getScore() < 100) {
			score.setLocation(735, 50);
		} else if (playField.getScore().getScore() < 1000) {
			score.setLocation(730, 50);
		} else if (playField.getScore().getScore() < 10000) {
			score.setLocation(725, 50);
		} else if (playField.getScore().getScore() < 100000) {
			score.setLocation(720, 50);
		}

		addTileButtons(playFieldPanel);
	}

	/**
	 * Method adds tile buttons an a panel
	 * @param panel
	 */
	private void addTileButtons(JPanel panel) {

		int buttonSize = 0; // Changes tiles field location

		int x = 180;
		int y = 60;

		switch (playField.getSize()) {
		case 4:
			buttonSize = 120;
			break;

		case 5:
			buttonSize = 96;
			break;

		case 6:
			buttonSize = 80;
			break;

		case 8:
			buttonSize = 60;
			break;

		default:
			break;
		}

		for (int i = 0; i < tileButtons.length; i++) {
			x = 180;
			y += buttonSize;
			for (int j = 0; j < tileButtons.length; j++) {
				tileButtons[i][j].setLocation(x, y);
				panel.add(tileButtons[i][j]);
				x += buttonSize;
			}
		}
	}

	/**
	 * Method updates tile buttons array
	 */
	private void updateTileButtons() {
		Tile[][] tiles = playField.getTilesArray();

		for (int i = 0; i < tileButtons.length; i++) {
			for (int j = 0; j < tileButtons.length; j++) {
				if (playField.isNull(tiles[i][j])) {
					tileButtons[i][j].setText("");
					
				} else {
					tileButtons[i][j].setText("" + tiles[i][j].getValue());
					
				}
			}
		}
	}

	/**
	 * Method sets color for all tile buttons in an array
	 */
	private void changeButtonsColor() {
		Tile[][] tiles = playField.getTilesArray();

		for (int i = 0; i < tileButtons.length; i++) {
			for (int j = 0; j < tileButtons.length; j++) {
				if (playField.isNull(tiles[i][j])) {
					tileButtons[i][j].setBackground(colorDefault);
				} else {
					tileButtons[i][j].setText("" + tiles[i][j].getValue());
					if (tiles[i][j].getValue() == 2) {
						tileButtons[i][j].setBackground(color2);
					} else if (tiles[i][j].getValue() == 4) {
						tileButtons[i][j].setBackground(color4);
					} else if (tiles[i][j].getValue() == 8) {
						tileButtons[i][j].setBackground(color8);
					} else if (tiles[i][j].getValue() == 16) {
						tileButtons[i][j].setBackground(color16);
					} else if (tiles[i][j].getValue() == 32) {
						tileButtons[i][j].setBackground(color32);
					} else if (tiles[i][j].getValue() == 64) {
						tileButtons[i][j].setBackground(color64);
					} else if (tiles[i][j].getValue() == 128) {
						tileButtons[i][j].setBackground(color128);
					} else if (tiles[i][j].getValue() == 256) {
						tileButtons[i][j].setBackground(color256);
					} else if (tiles[i][j].getValue() == 512) {
						tileButtons[i][j].setBackground(color512);
					} else if (tiles[i][j].getValue() == 1024) {
						tileButtons[i][j].setBackground(color1024);
					} else if (tiles[i][j].getValue() == 2048) {
						tileButtons[i][j].setBackground(color2048);
					} else if (tiles[i][j].getValue() == 4096) {
						tileButtons[i][j].setBackground(color4096);
					} else {
						tileButtons[i][j].setBackground(Color.BLACK);
					}
				}
			}
		}
	}

	/**
	 * Method updates tile array tile adds new tile if move were possible,
	 * updates tile buttons array and checks if game is not over, when user made
	 * move to the right
	 */
	public void moveRight() {
		if (playField.moveToTheRight()) {
			addTile();
		}
		updateTileButtons();
		if (playField.isFull() && !playField.isStepsAvailable()) {
			gameOver();
			return;
		}
		changeButtonsColor();
		updatePlayFieldPanel();

	}

	/**
	 * Method updates tile array tile adds new tile if move were possible,
	 * updates tile buttons array and checks if game is not over, when user made
	 * move to the left
	 */
	public void moveLeft() {
		if (playField.moveToTheLeft()) {
			addTile();
		}
		updateTileButtons();
		if (playField.isFull() && !playField.isStepsAvailable()) {
			gameOver();
			return;
		}
		changeButtonsColor();
		updatePlayFieldPanel();
	}

	/**
	 * Method updates tile array tile adds new tile if move were possible,
	 * updates tile buttons array and checks if game is not over, when user made
	 * move up
	 */
	public void moveUp() {
		if (playField.moveUp()) {
			addTile();
		}
		updateTileButtons();
		if (playField.isFull() && !playField.isStepsAvailable()) {
			gameOver();
			return;
		}
		changeButtonsColor();
		updatePlayFieldPanel();
	}

	/**
	 * Method updates tile array tile adds new tile if move were possible,
	 * updates tile buttons array and checks if game is not over, when user made
	 * move down
	 */
	public void moveDown() {
		if (playField.moveToTheBottom()) {
			addTile();
		}
		updateTileButtons();
		if (playField.isFull() && !playField.isStepsAvailable()) {
			gameOver();
			return;
		}
		changeButtonsColor();
		updatePlayFieldPanel();
	}

	/**
	 * Method adds game over label on a panel and deletes stored game
	 */
	private void gameOver() {
		playFieldPanel.add(gameOver);
		playFieldPanel.setFocusable(false);
		try {
			new DBWorker().deletePlayField(user);
		} catch (InstantiationException e) {
			logger.log(Level.SEVERE, "Exception: ", e);
		} catch (IllegalAccessException e) {
			logger.log(Level.SEVERE, "Exception: ", e);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, "Exception: ", e);
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, "Exception: ", e);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Exception: ", e);
		}
	}

	/**
	 * Method adds new tile on a play field
	 */
	private void addTile(){
		playField.addTile(new Tile((int) (Math.random() * (playField.getSize() - 1)), (int) (Math.random() * playField.getSize())));
	}
}
