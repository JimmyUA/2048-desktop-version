package GUI;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import playField.PlayField;
import playField.Tile;
import user.User2048;
import dataBase.DBWorker;

/**
 * Class creates a window where you can chose a play field size,
 * login and signin
 * 
 * @author Sergey
 * v.6.33.4
 */

public class FirstWindow extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private Logger logger;
	private FileHandler handler;

	private User2048 user;
	private PlayField playField;

	private int size; // size of a play field

	private int max;
	private int min;

	// Chose size panel elements
	private JLabel title;
	private JLabel sizeLabel;
	private JLabel forImage;
	private JLabel userName;

	private Buttons logIn;
	private Buttons next;
	private Buttons previous;
	private Buttons playButton;
	private Buttons resumeButton;
	private Buttons logOut;
	private Buttons signIn;

	private ImageIcon fourXfour;
	private ImageIcon fiveXfive;
	private ImageIcon sixXsix;
	private ImageIcon eightXeight;

	// Sign in panel elements
	private JLabel signInTitle;
	private JLabel sName;
	private JLabel sLogin;
	private JLabel sPassword;
	private JLabel sConfPass;
	private JLabel email;
	private JLabel warning;
	private JLabel shouldEnterAll;

	private JTextField lTextLogin;
	private JPasswordField lTextPass;
	private JTextField sTextName;
	private JTextField sTextLogin;
	private JPasswordField sTextPassword;
	private JPasswordField sTextConfPass;
	private JTextField emailText;

	private JButton sSubmit;

	{
		size = 4;
		max = 8;
		min = 4;

		// Initializing size panel elements
		title = new Labels("2048");
		title.setFont(title.getFont().deriveFont(64f));

		forImage = new JLabel();
		shouldEnterAll = new JLabel();
		shouldEnterAll.setFont(shouldEnterAll.getFont().deriveFont(18f));
		shouldEnterAll.setForeground(Color.RED);

		sizeLabel = new Labels("4x4");
		sizeLabel.setFont(sizeLabel.getFont().deriveFont(40f));

		warning = new Labels();
		warning.setFont(warning.getFont().deriveFont(10f));

		signIn = new Buttons("Sign In");
		signIn.setFont(signIn.getFont().deriveFont(8f));
		logIn = new Buttons("Log In");
		logIn.setFont(logIn.getFont().deriveFont(8f));
		logOut = new Buttons("Log Out");

		playButton = new BigButtons("NEW GAME");
		// playButton.setFont(playButton.getFont().deriveFont(24f));
		// playButton.setSize(200, 60);

		resumeButton = new BigButtons("RESUME");
		// resumeButton.setFont(resumeButton.getFont().deriveFont(24f));
		// resumeButton.setSize(200, 60);

		next = new Buttons(">");
		next.setFont(playButton.getFont().deriveFont(36f));
		next.setSize(60, 60);
		next.setBorder(null);

		previous = new Buttons("<");
		previous.setFont(playButton.getFont().deriveFont(36f));
		previous.setSize(60, 60);
		previous.setBorder(null);

		fourXfour = new ImageIcon("4x4.jpg");
		fiveXfive = new ImageIcon("5x5.jpg");
		sixXsix = new ImageIcon("6x6.jpg");
		eightXeight = new ImageIcon("8x8.jpg");

		// Initializing sign in panel elements

		signInTitle = new Labels("Please fullfil following fields:");
		signInTitle.setFont(signInTitle.getFont().deriveFont(24f));

		sName = new Labels("Name*:");
		sName.setFont(sName.getFont().deriveFont(24f));

		sLogin = new Labels("Login*:");
		sLogin.setFont(sLogin.getFont().deriveFont(24f));

		sPassword = new Labels("Password*:");
		sPassword.setFont(sPassword.getFont().deriveFont(24f));

		sConfPass = new Labels("Confirm password*:");
		sConfPass.setFont(sConfPass.getFont().deriveFont(24f));

		email = new Labels("Email*:");
		email.setFont(title.getFont().deriveFont(24f));

		lTextLogin = new TextFields();
		lTextPass = new JPasswordField();
		lTextPass.setFont(lTextPass.getFont().deriveFont(24f));
		lTextPass.setSize(500, 65);
		sTextName = new TextFields();
		sTextLogin = new TextFields();
		sTextPassword = new JPasswordField();
		sTextPassword.setFont(sTextPassword.getFont().deriveFont(24f));
		sTextPassword.setSize(500, 65);
		sTextConfPass = new JPasswordField();
		sTextConfPass.setFont(sTextConfPass.getFont().deriveFont(24f));
		sTextConfPass.setSize(500, 65);
		emailText = new TextFields();

		sSubmit = new BigButtons("SUBMIT");

		logger = Logger.getLogger(GUI2048.class.getName());
		try {
			handler = new FileHandler("log.txt");
			logger.addHandler(handler);
		} catch (SecurityException e) {
			logger.log(Level.SEVERE, "Can't create file due to security settings");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Can't create file due to IO problems");
			e.printStackTrace();
		}
	}

	// First window constructor

	public FirstWindow() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException,
			FileNotFoundException {
		super("2048 v. 6.33.4");
		user = new DBWorker().getLastUser();
		user.getRecord().setRecord(user);
		Image img = new ImageIcon("2048.jpg").getImage();
		setIconImage(img);
		setSize(850, 850);
		setVisible(true);
		setResizable(false);
		setBackground(Color.BLACK);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(500, 0);
		if (user != null) {
			user.setField(new PlayField(size, user.getScore()));
			playField = user.getField();
		}

		setContentPane(get4SizeWindow());
		initListeners();

	}
	
	/**
	 * Method sets user object
	 * @param user
	 *  
	 */
	public void setUser(User2048 user) {
		this.user = user;
	}

	/**
	 * Method sets listeners for all buttons on a frame
	 */
	@SuppressWarnings("deprecation")
	private void initListeners() {

		checkIfStoredFieldExist();

		logIn.addActionListener(event -> {

			String login = lTextLogin.getText();
			String pass = lTextPass.getText();
			System.out.println(pass);
			if (login.equals("") || pass.equals("")) {
				warning.setText("All fields should be filled in!");
			} else {
				try {
					user = new DBWorker().getUser(login, pass);

				} catch (InstantiationException e1) {
					logger.log(Level.SEVERE, "Exception: ", e1);
				} catch (IllegalAccessException e1) {
					logger.log(Level.SEVERE, "Exception: ", e1);
				} catch (ClassNotFoundException e1) {
					logger.log(Level.SEVERE, "Exception: ", e1);
				} catch (SQLException e1) {
					logger.log(Level.SEVERE, "Exception: ", e1);
				} catch (FileNotFoundException e1) {
					logger.log(Level.SEVERE, "Exception: ", e1);
				}

				if (user == null) {
					warning.setText("Login or password is not correct");
				} else {
					user.setField(new PlayField(size, user.getScore()));
					playButton.setEnabled(true);
					resumeButton.setEnabled(true);
					setContentPane(get4SizeWindow());
					checkIfStoredFieldExist();
					setVisible(true);
				}
				lTextPass.setText(null);
				lTextLogin.setText(null);

			}

			

		});

		next.addActionListener(event -> {

			if (size == 6) {
				size++;
			}
			if (size < max) {
				size++;
			}
			if (user != null) {
				checkIfStoredFieldExist();
			}
			sizeLabel.setText(size + "x" + size);
			setContentPane(get4SizeWindow());
			setVisible(true);
		});

		previous.addActionListener(event -> {

			if (size == 8) {
				size--;
			}
			if (size > min) {
				size--;
			}
			if (user != null) {
				checkIfStoredFieldExist();
			}
			sizeLabel.setText(size + "x" + size);
			setContentPane(get4SizeWindow());
			setVisible(true);
		});

		playButton.addActionListener(event -> {

			user.getField().setSize(size);
			user.getField().setTilesArray();
			user.getField().addTile(new Tile(size - (int) (Math.random() * 2 + 1), (int) (Math.random() * size)));
			user.getField().addTile(new Tile(size - (int) (Math.random() * 2 + 1), (int) (Math.random() * size)));
			new GUI2048(user);
			setVisible(false);
		});

		resumeButton.addActionListener(event -> {
			user.getField().setSize(size);
			try {

				playField.setSavedTilesArray(new DBWorker().getStoredField(user));
			} catch (InstantiationException e1) {
				logger.log(Level.SEVERE, "Exception: ", e1);
			} catch (IllegalAccessException e1) {
				logger.log(Level.SEVERE, "Exception: ", e1);
			} catch (ClassNotFoundException e1) {
				logger.log(Level.SEVERE, "Exception: ", e1);
			} catch (SQLException e1) {
				logger.log(Level.SEVERE, "Exception: ", e1);
			} catch (FileNotFoundException e1) {
				logger.log(Level.SEVERE, "Exception: ", e1);
			}
			new GUI2048(user);
			setVisible(false);
		});

		signIn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setContentPane(getSignInPanel());
				setVisible(true);

			}
		});

		sSubmit.addActionListener(event -> {

			String name = sTextName.getText();
			String login = sTextLogin.getText();
			String pass = sTextPassword.getText();
			String email = emailText.getText();

			try {
				if (new DBWorker().isUserExsist(login)) {
					shouldEnterAll.setText("This login is already occupied, plese chose another login!");
					// setContentPane(getSignInPanel());
					setVisible(true);
				}
				if (!name.equals("") && !login.equals("") && !pass.equals("") && !email.equals("")) {

					if (new DBWorker().isUserExsist(login)) {
						shouldEnterAll.setSize(600, 40);
						shouldEnterAll.setLocation(200, 100);
						shouldEnterAll.setText("This login is already occupied, plese chose another login!");
						sTextLogin.setText(null);;
					} else if (!pass.equals(sTextConfPass.getText())) {
						shouldEnterAll.setLocation(300, 100);
						shouldEnterAll.setText("Passwords should be equals!!!");
						sTextPassword.setText(null);;
						sTextConfPass.setText(null);;
					} else if (!testEmail(email)) {
						shouldEnterAll.setLocation(350, 100);
						shouldEnterAll.setText("Email is not valid!!!");
						emailText.setText(null);;
					} else {
						try {
							user = new User2048(name, login, pass, email);
							user.setField(new PlayField(size, user.getScore()));
							if (user.getRecord().getRecord() > 0) {
								user.getRecord().setRecord(user);
							}
							new DBWorker().saveUser(user);
						} catch (InstantiationException e1) {
							logger.log(Level.SEVERE, "Exception: ", e1);
						} catch (IllegalAccessException e1) {
							logger.log(Level.SEVERE, "Exception: ", e1);
						} catch (ClassNotFoundException e1) {
							logger.log(Level.SEVERE, "Exception: ", e1);
						} catch (SQLException e1) {
							logger.log(Level.SEVERE, "Exception: ", e1);
						} catch (FileNotFoundException e1) {
							logger.log(Level.SEVERE, "Exception: ", e1);
						}
						if (user != null){
							playButton.setEnabled(true);
						}
						checkIfStoredFieldExist();
						setContentPane(get4SizeWindow());
						setVisible(true);
					}
				} else {
					shouldEnterAll.setLocation(300, 100);
					shouldEnterAll.setText("You should fill all fields!!!");
					// setContentPane(getSignInPanel());
					setVisible(true);
				}
			} catch (InstantiationException e1) {
				logger.log(Level.SEVERE, "Exception: ", e1);
			} catch (IllegalAccessException e1) {
				logger.log(Level.SEVERE, "Exception: ", e1);
			} catch (ClassNotFoundException e1) {
				logger.log(Level.SEVERE, "Exception: ", e1);
			} catch (FileNotFoundException e1) {
				logger.log(Level.SEVERE, "Exception: ", e1);
			} catch (SQLException e1) {
				logger.log(Level.SEVERE, "Exception: ", e1);
			}
			
		});

		logOut.addActionListener(event -> {

			user = null;
			setContentPane(get4SizeWindow());
			playButton.setEnabled(false);
			resumeButton.setEnabled(false);
			setVisible(true);
		});
	}

	/**
	 * Creates a panel with current size of a play field
	 * 
	 * @return panel introduses current play field size
	 */

	private JPanel get4SizeWindow() {

		JPanel window4Size = new JPanel();
		window4Size.setLayout(null);
		window4Size.setBackground(Color.BLACK);

		warning.setSize(200, 20);
		warning.setLocation(680, 10);

		title.setSize(200, 65);
		title.setLocation(340, 40);

		sizeLabel.setSize(200, 65);
		sizeLabel.setLocation(400, 500);

		lTextLogin.setFont(lTextLogin.getFont().deriveFont(12f));
		lTextLogin.setSize(120, 20);
		lTextLogin.setLocation(700, 30);

		lTextPass.setFont(lTextPass.getFont().deriveFont(12f));
		lTextPass.setSize(120, 20);
		lTextPass.setLocation(700, 50);

		logIn.setSize(60, 20);
		logIn.setLocation(700, 70);

		logOut.setSize(100, 20);
		logOut.setLocation(720, 60);

		signIn.setSize(60, 20);
		signIn.setLocation(700, 100);

		playButton.setLocation(340, 600);
		resumeButton.setLocation(340, 680);
		next.setLocation(600, 500);
		previous.setLocation(200, 500);
		forImage.setLocation(280, 80);
		forImage.setSize(500, 500);
		forImage.setVisible(true);

		switch (size) {
		case 4:
			forImage.setIcon(fourXfour);
			break;
		case 5:
			forImage.setIcon(fiveXfive);
			break;
		case 6:
			forImage.setIcon(sixXsix);
			break;
		case 8:
			forImage.setIcon(eightXeight);
			break;
		default:
			break;

		}
		// adding elements to a frame

		window4Size.add(title);
		window4Size.add(playButton);
		window4Size.add(resumeButton);
		window4Size.add(sizeLabel);
		window4Size.add(forImage);

		if (user == null) {
			window4Size.add(warning);
			window4Size.add(signIn);
			window4Size.add(logIn);
			window4Size.add(lTextLogin);
			window4Size.add(lTextPass);
		} else {
			userName = new Labels(user.getName());
			userName.setFont(title.getFont().deriveFont(18f));
			if (user.getName().length() < 6) {
				userName.setLocation(750, 30);
			} else if (user.getName().length() < 10) {
				userName.setLocation(730, 30);
			} else {
				userName.setLocation(700, 30);
			}
			userName.setSize(150, 20);
			window4Size.add(userName);
			window4Size.add(logOut);
		}
		window4Size.add(next);
		window4Size.add(previous);
		// window4Size.add(forImage);

		return window4Size;
	}

	/**
	 * Method creates "sign in" panel
	 * @return "sign in" panel
	 */
	private JPanel getSignInPanel() {
		JPanel signInPanel = new JPanel();
		signInPanel.setLayout(null);
		signInPanel.setBackground(Color.BLACK);

		signInTitle.setSize(500, 65);
		signInTitle.setLocation(260, 40);

		sName.setSize(300, 65);
		sName.setLocation(100, 150);

		sTextName.setLocation(150, 200);

		sLogin.setSize(300, 65);
		sLogin.setLocation(100, 250);

		sTextLogin.setLocation(150, 300);

		email.setSize(500, 65);
		email.setLocation(100, 350);

		emailText.setLocation(150, 400);

		sPassword.setSize(500, 65);
		sPassword.setLocation(100, 450);

		sTextPassword.setLocation(150, 500);

		sConfPass.setSize(500, 65);
		sConfPass.setLocation(100, 550);

		sTextConfPass.setLocation(150, 600);

		sSubmit.setSize(200, 50);
		sSubmit.setLocation(300, 700);

		shouldEnterAll.setSize(400, 40);
		shouldEnterAll.setLocation(280, 100);

		signInPanel.add(signInTitle);
		signInPanel.add(sName);
		signInPanel.add(sTextName);
		signInPanel.add(sLogin);
		signInPanel.add(sTextLogin);
		signInPanel.add(email);
		signInPanel.add(emailText);
		signInPanel.add(sPassword);
		signInPanel.add(sTextPassword);
		signInPanel.add(sConfPass);
		signInPanel.add(sTextConfPass);
		signInPanel.add(sSubmit);
		signInPanel.add(shouldEnterAll);

		return signInPanel;

	}

	/**
	 * Returns size currently chosen by user
	 * @return size
	 */
	public int returnSize() {
		return size;
	}

	/**
	 * Method returns user object 
	 * @return the user
	 */
	public User2048 getUser() {
		return user;
	}

	/**
	 * Method checkes is in database there is stored play field
	 * if not set's "Resume" button disabled
	 */
	private void checkIfStoredFieldExist() {
		try {
			resumeButton.setEnabled(true);
			if (user != null && !new DBWorker().isStoredFieldExist(user, size)) {
				resumeButton.setEnabled(false);
			}

		} catch (InstantiationException e1) {
			logger.log(Level.SEVERE, "Exception: ", e1);
		} catch (IllegalAccessException e1) {
			logger.log(Level.SEVERE, "Exception: ", e1);
		} catch (ClassNotFoundException e1) {
			logger.log(Level.SEVERE, "Exception: ", e1);
		} catch (FileNotFoundException e1) {
			logger.log(Level.SEVERE, "Exception: ", e1);
		} catch (SQLException e1) {
			logger.log(Level.SEVERE, "Exception: ", e1);
		}
	}

	/**
	 * Method checks if the string suites regular expression for e-mails
	 * @param testString
	 * @return true if the tested string suites regular expression for e-mails 
	 * and false otherwise
	 */
	private static boolean testEmail(String testString) {
		Pattern p = Pattern
				.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher m = p.matcher(testString);
		return m.matches();
	}
}
