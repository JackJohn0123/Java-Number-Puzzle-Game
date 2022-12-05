import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.border.Border;


public class NumPuz extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Random ran = new Random();
	NumPuz frame;
	Color gridColor;
	
	public int seconds = 0;
	int minutes = 0;
	int gridSize = 3;
	int invisibleHeight = 0;
	int invisibleRow = 0;
	int maxGap = 10;

	static final Integer[] sizeOptions = { 3, 4, 5, 6 };
	static final String[] templateOptions = { "Numbers", "Text" };
	
	JComboBox comboBoxSize;
	JComboBox comboBoxGrid;

	JMenu Mfile = new JMenu("FILE");
	JMenu Mhelp = new JMenu("Help");
	
	JMenuItem openMenu = new JMenuItem("New", new ImageIcon("iconnew.png"));
	JMenuItem saveMenu = new JMenuItem("Solution", new ImageIcon("iconsol.png"));
	JMenuItem hostServer = new JMenuItem("Host Server", new ImageIcon("iconsol.png"));
	JMenuItem joinServer = new JMenuItem("Join Server", new ImageIcon("iconsol.png"));
	JMenuItem exitMenu = new JMenuItem("Exit", new ImageIcon("iconext.png"));
	JMenuItem colors = new JMenuItem("Colors", new ImageIcon("iconcol.png"));
	JMenuItem about = new JMenuItem("About", new ImageIcon("iconabt.png"));
	
	String[][] textGridArray = new String[6][6];
	
	public String[][] getTextGridArray() {
		return textGridArray;
	}

	JButton applyButton = new JButton("Apply grid size");
	JButton[][] buttons = new JButton[10][10];
	JButton Blogo = new JButton();
	JButton button;
	JButton Bload = new JButton("Load");
	JButton Bsave = new JButton("Save");
	JButton Bmode = new JButton("Mode:");
	JButton BsetNames = new JButton("Set grid names:");
	JButton Btemplate = new JButton("Apply template");
	JButton Bshow = new JButton("Show");
	JButton Bhide = new JButton("Hide");
	JButton Brandom = new JButton("Rand");
	JButton Bfinish = new JButton("Finish");
	
	JScrollPane scrollPane;
	JTextArea textField = new JTextArea();
	
	JTextField setNameTextBox = new JTextField(100);

	JPanel gameBoard = new JPanel();
	JPanel topControls = new JPanel();
	JPanel bottomControls = new JPanel();
	JPanel title = new JPanel();
	JPanel sideGrid = new JPanel();

	static JMenuBar mb = new JMenuBar();
	
	JFileChooser fileDirectory = new JFileChooser();


	JTextField points = new JTextField("points");
	JTextField time = new JTextField("time:");

	Timer timer = new Timer();
	TimerTask timerTask;
	
	JRadioButton design = new JRadioButton("Design");
	JRadioButton play = new JRadioButton("Play");

	
	public JMenuItem getHostServer() {
		return hostServer;
	}

	public JMenuItem getJoinServer() {
		return joinServer;
	}
	public JButton getBsetNames() {
		return BsetNames;
	}

	public JMenuItem getAboutItem() {
		return about;
	}

	public JMenu getMhelp() {
		return Mhelp;
	}

	public JButton[][] getButtons() {
		return buttons;
	}

	public JMenuItem getOpenMenu() {
		return openMenu;
	}

	public JMenuItem getSaveMenu() {
		return saveMenu;
	}

	public JMenuItem getExitMenu() {
		return exitMenu;
	}

	public JMenuItem getColors() {
		return colors;
	}

	public JMenuItem getAbout() {
		return about;
	}
	


	public JRadioButton getDesign() {
		return design;
	}

	public JPanel getGameBoard() {
		return gameBoard;
	}

	public JRadioButton getPlay() {
		return play;
	}

	public JButton getBload() {
		return Bload;
	}

	public JButton getBmode() {
		return Bmode;
	}

	public Color gridColor() {
		return gridColor;
	}

	public JButton getApplyButton() {
		return applyButton;
	}

	public void setApplyButton(JButton applyButton) {
		this.applyButton = applyButton;
	}

	public JButton getBtemplate() {
		return Btemplate;
	}

	public JButton getBshow() {
		return Bshow;
	}

	public JButton getBhide() {
		return Bhide;
	}

	public void setTextField(String coordinates) {
		textField.setText(textField.getText() + coordinates + "\n");

	}

	public JButton getBfinish() {
		return Bfinish;
	}

	public JButton getBrandom() {
		return Brandom;
	}

	public JButton getBsave() {
		return Bsave;
	}

	public void setBsave(JButton bsave) {
		Bsave = bsave;
	}

	public JComboBox getComboBoxGrid() {
		return comboBoxGrid;
	}
	
	public NumPuz(String name) {
		super(name);
		setResizable(false);
	}

	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

	public int gridSize() {
		return gridSize;
	}

	public void gridSize(int size) {
		this.gridSize = size;
	}

	public JComboBox getComboBoxSize() {
		return comboBoxSize;
	}

	protected boolean isNull(int Dialog) {

		if (Dialog == 1) {
			return true;
		}

		return false;
	}

	void textField() {

	}
	/* add components to the container */
	public void addComponentsToPane(final Container pane) {
		
		mb.add(Mfile);
		mb.add(Mhelp);
		Mfile.add(openMenu);
		Mfile.add(saveMenu);
		Mfile.add(joinServer);
		Mfile.add(hostServer);
		Mfile.add(exitMenu);
		Mhelp.add(colors);
		Mhelp.add(about);
		title.setLayout(new GridLayout(1, 1));

		bottomControls.setLayout(new GridLayout(2, 3,50, 5));

		GridBagConstraints to = new GridBagConstraints();

		button = new JButton("t 1");

		title.add(button);

		bottomControls.setPreferredSize(new Dimension((int) (4 * 18.0) + maxGap, (int) (1 * 30.5) + maxGap * 5));

		to.gridx = 0;
		to.gridy = 1;

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		Bmode.setBorderPainted(false);
		Bmode.setContentAreaFilled(false);
		Bmode.setFocusPainted(false);
		ButtonGroup mode = new ButtonGroup();
		mode.add(play);
			
		to.fill = GridBagConstraints.HORIZONTAL;
		comboBoxGrid = new JComboBox(templateOptions);
		bottomControls.add(comboBoxGrid, to);
		bottomControls.add(Btemplate, to);
		bottomControls.add(setNameTextBox, to);
		bottomControls.add(BsetNames, to);
		bottomControls.add(Brandom, to);
		bottomControls.add(Bfinish, to);
		bottomControls.add(Box.createHorizontalStrut(80), to);
		bottomControls.add(play, to);
		mode.add(design);
		bottomControls.add(design, to);
		design.setSelected(true);
		Bfinish.setEnabled(false);

		bottomControls.add(mb);

		topControls.setLayout(new GridLayout(2, 3));

		// Set up components preferred size
		JButton b = new JButton("Just fake button");
		Dimension buttonSize = b.getPreferredSize();
		textField.setPreferredSize(new Dimension((int) (buttonSize.getWidth() * 2.0) + maxGap,
				(int) (buttonSize.getHeight() * 20.5) + maxGap * 1));
		textField.setEditable(false);
		textField.setBorder(BorderFactory.createLineBorder(Color.black));

		Blogo.setIcon(new ImageIcon("gamelogo.png"));
		Blogo.setBorderPainted(false);
		Blogo.setBackground(Color.white);

		topControls.setLayout(new GridLayout(3, 2, 100, 10));
		topControls.setMaximumSize(new Dimension(620, 200));

		comboBoxSize = new JComboBox(sizeOptions);
		topControls.add(mb);
		topControls.add(Blogo);
		topControls.add(Box.createHorizontalStrut(10));
		topControls.add(comboBoxSize);
		topControls.add(time);
		// points
		topControls.add(Bsave); // show
		topControls.add(applyButton);
		topControls.add(points); // points
		// title
		topControls.add(Bload); // load

		sideGrid.add(textField);
		sideGrid.add(scrollPane);
		scrollPane.setVisible(true);
		pane.add(gameBoard, BorderLayout.CENTER);
		pane.add(topControls, BorderLayout.NORTH);
		pane.add(sideGrid, BorderLayout.EAST);
		pane.add(bottomControls, BorderLayout.AFTER_LAST_LINE);

	}

	/* read txt files */
	void read(String string, String path) {
		String buttonText = " ";
		String concanatedText = "";
		int row = 0;
		int column = 0;
		try {
			Scanner fileReader = new Scanner(new File(path + string));
			gameBoard.removeAll();
			gridSize = fileReader.nextInt();
			gridSize(gridSize);
			gameBoard.setLayout(new GridLayout(gridSize, gridSize));			
			fileReader.nextLine();
			String gameType = fileReader.nextLine();
			if(gameType.equals("Text")) {
				comboBoxGrid.setSelectedIndex(1);
			}
			if(gameType.equals("Numbers")) {
				comboBoxGrid.setSelectedIndex(0);
			}
			while (fileReader.hasNext()) {
				row = fileReader.nextInt();
				column = fileReader.nextInt();
				fileReader.nextLine();
				buttonText = fileReader.nextLine();
				concanatedText = concanatedText + buttonText;
				buttons[row][column] = new JButton(buttonText);
				gameBoard.add(buttons[row][column]);
				buttons[row][column].setText(buttonText);
				if (buttonText.equalsIgnoreCase(".")) {
					buttons[row][column].setText(".");
					buttons[row][column].setVisible(false);
					play.doClick();
				}
			}
			gameBoard.revalidate();
			gameBoard.repaint();
		} catch (FileNotFoundException e) {

		}

	}
	/* writes the grid into a txt file */
	public void write(String string, String path) {
		try {
			System.out.println(gridSize());
			PrintWriter pw = new PrintWriter(new FileWriter(path + string));
			pw.format("%1s", gridSize + "\n");
			pw.format("%1s",getTemplateOptions() + "\n");
			for (int r = 0; r < gridSize; r++) {
				for (int w = 0; w < gridSize; w++) {
					pw.format("%1s", w + "\n");
					pw.format("%1s", r + "\n");
					pw.format("%1s", buttons[w][r].getText() + "\n");
				}
			}
			pw.close();
		} catch (IOException e) {
			System.out.println("an error occured");
		}
	}
	/* cretaes a new grid*/
	public void newGrid(NumPuzController controller) {
		gameBoard.setLayout(new GridLayout(gridSize, gridSize));
		for (int h = 0; h < getGridSize(); h++)
			for (int r = 0; r < gridSize; r++) {
				buttons[r][h] = new JButton("row " + r + " height " + h);
				gameBoard.add(buttons[r][h]);
				button.addActionListener(controller);

			}

	}

	public int getGridSize() {
		return gridSize;
	}
	/*  creates a new custom user made grid */
	public void newGridText(String word) {
		if (word.length() < gridSize * gridSize) {
			JOptionPane.showMessageDialog(frame, "The entered text is not long enough for the grid! A default word would be used instead.");	
			word = "THE TEXT YOU ENTERED IS NOT LONG ENOUGH PLEASE TRY AGAIN AND MAKE IT LONGER";

		}
		String[] s = word.split("");

		int count = 0;
		for (int h = 0; h < gridSize; h++)
			for (int r = 0; r < gridSize; r++) {
				textGridArray[r][h] = s[count];
				buttons[r][h].setText(textGridArray[r][h]);
				count++;

			}

	}
	/* change grid into the destinated size */
	public void changeGrid(int size, NumPuzController controller) {
		gameBoard.removeAll();

		gameBoard.setLayout(new GridLayout(gridSize, gridSize));
		for (int h = 0; h < gridSize; h++)
			for (int r = 0; r < gridSize; r++) {
				buttons[r][h] = new JButton("row " + r + " height " + h);
				gameBoard.add(buttons[r][h]);
				buttons[r][h].addActionListener(controller);
				buttons[r][h].setActionCommand("Grid" + " " + r + "-" + h);
			}
		gameBoard.repaint();
		gameBoard.revalidate();
	}
	/* register the listeners for all the components */
	public void registerListener(NumPuzController controller) {
		Component[] topcomponents = topControls.getComponents();
		Component[] bottomcomponents = bottomControls.getComponents();
		for (Component component : bottomcomponents) {
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.addActionListener(controller);
			}

		}
		for (Component component : topcomponents) {
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.addActionListener(controller);
			}

		}
		exitMenu.addActionListener(controller);
		colors.addActionListener(controller);
		about.addActionListener(controller);
		openMenu.addActionListener(controller);
		hostServer.addActionListener(controller);
		joinServer.addActionListener(controller);
	}
	/* randomize the grids */
	public void randomize() {
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				int i1 = (int) (Math.random() * gridSize);
				int j1 = (int) (Math.random() * gridSize);
				String temp = buttons[i][j].getText();
				buttons[i][j].setText(buttons[i1][j1].getText());
				buttons[i1][j1].setText(temp);
			}

		}

	}
	/* starts the timer */
	public void startTimer(boolean choice) {
		if (choice) {
			TimerTask timerTask = new TimerTask() {

				@Override
				public void run() {
					seconds++;
					time.setText(String.valueOf("Time Minutes: " + minutes + " Seconds: " + seconds));
					seconds = seconds % 60;
					minutes += seconds == 0 ? 1 : 0;

				}
			};
			timer = new Timer();
			timer.scheduleAtFixedRate(timerTask, 0, 1000);
		}

		if (!choice) {
			time.setText(String.valueOf("Time Minutes: " + minutes + " Seconds: " + seconds));
			TimerTask timerTask = new TimerTask() {
				@Override
				public void run() {

				}
			};
			minutes = 0;
			seconds = 0;
			timer.cancel();
			timer = new Timer();
			timer.schedule(timerTask, 1000);
		}
	}
	/* gets jcolorchooser to set the color of the grid */
	public void colorGridLayout() {
		Color initialcolor = Color.RED;
		Color color = JColorChooser.showDialog(this, "Select a color", initialcolor);
		gridColor = color;
		for (int h = 0; h < gridSize; h++)
			for (int r = 0; r < gridSize; r++) {
				buttons[r][h].setBackground(color);

			}

	}
	/* start the game */
	public void startGame() {
		invisibleHeight = (int) (Math.random() * gridSize);
		invisibleRow = (int) (Math.random() * gridSize);
		buttons[invisibleHeight][invisibleRow].setText(".");
		buttons[invisibleHeight][invisibleRow].setVisible(false);
		textGridArray[invisibleHeight][invisibleRow] = ".";

	}
	/* checks if one of the grid is missing */
	public boolean hasGameStarted() {
		for (int h = 0; h < gridSize; h++)
			for (int r = 0; r < gridSize; r++) {
				if (!buttons[r][h].isVisible()) {
					return true;
				}
				if (buttons[r][h].getText() == "." && buttons[r][h].isVisible()) {
					buttons[r][h].setVisible(false);
					return true;
				}

			}

		return false;
	}
	/* sets all the grid visible */
	public void setAllVisible() {
		for (int h = 0; h < gridSize; h++)
			for (int r = 0; r < gridSize; r++) {
				buttons[r][h].setVisible(true);
			}
	}
	
	public void setPoints(double score) {
		points.setText("Point: " + String.valueOf(score));
	}

	public double getSeconds() {

		return (double) seconds;
	}

	public boolean isModeSelectedPlay() {

		return play.isSelected();
	}

	public boolean isModeSelectedDesign() {

		return design.isSelected();
	}

	public String getTemplateOptions() {
		return String.valueOf(comboBoxGrid.getSelectedItem());
	}

	public void getHelp() {
		JOptionPane.showMessageDialog(null,
				"Game Created by Jack Johnston! \n Move the tiles around to the correct place, green tiles indicates a correct position!",
				"About", JOptionPane.QUESTION_MESSAGE);
	}

	public void setColorDefault() {
		JButton Test = new JButton();
		for (int h = 0; h < gridSize; h++)
			for (int r = 0; r < gridSize; r++) {
				buttons[r][h].setBackground(Test.getBackground());
			}
	}
	
	public String getSetNameTextBox() {
		String s = setNameTextBox.getText();
		return s;
		
	}

	public void message() {
		JOptionPane.showMessageDialog(frame, "A new grid 3x3 has been created");	
		
	}
}
