

import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class Client {
	
	private static final boolean RIGHT_TO_LEFT = false;
	JLabel connect, status;
	JLabel label = new JLabel("dw", JLabel.CENTER);
	JButton start;
	JButton results;
	JButton end;
	JTextField portField = new JTextField(3);
	JButton b, b1;
	JTextField b2, userName, serverName, portNumber;
	JLabel l;
	JButton user, server, port, Connect, End, newGame, sendGame, receiveGame, sendData, Play;
	PrintWriter cout;
	ThreadClient threadClient;
	JFrame frame = new JFrame("Client GUI");

	Socket socket = null;
	char Separator = '#';
	int maxTries = 5;
	int tries = 0;
	NumPuz view;
	NumPuzController controller;
	
    public Client(NumPuz view, NumPuzController controller) {
		this.view = view;
		this.controller = controller;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ThreadClient getThreadClient() {
		return threadClient;
	}

	public void setThreadClient(ThreadClient threadClient) {
		this.threadClient = threadClient;
	}

	public PrintWriter getCout() {
		return cout;
	}

	public void setCout(PrintWriter cout) {
		this.cout = cout;
	}

	private void endGame() {
        getCout().println(userName.getText() + Separator + "P0"); //WORKING SO FAR
		getCout().flush();

	}
	
	
	protected void sendData() {
		String time = view.time.getText();
		String points = view.points.getText();
		if(view.time.getText().equals("time:")) {
			time = "Timer Not Set";	
		}
		if(view.points.getText().equals("points")) {
			points = "Points not Set";	
		}
		getCout().println(userName.getText() + Separator + "P4" + Separator + points + Separator + time); 
		getCout().flush();
		
	}
	
	
	private Object sendGame() {
		String gridType = "";
		String gridContent = "";
		
		if(view.comboBoxGrid.getSelectedIndex() == 0) {
			gridType = "Numbers";

			for (int h = 0; h < view.getGridSize(); h++)
					for (int r = 0; r < view.getGridSize(); r++) {
						gridContent = gridContent + view.getButtons()[h][r].getText() + ','; 
					}
			getCout().println(userName.getText() + Separator + "P1" + Separator + view.getGridSize() + Separator + gridType + '#' + gridContent);
			getCout().flush();

		}
			 
		else {
			gridType = "Text";
			for (int h = 0; h < view.getGridSize(); h++)
				for (int r = 0; r < view.getGridSize(); r++) {
					gridContent = gridContent + view.getButtons()[h][r].getText() + ','; 
				}
			getCout().println(userName.getText() + Separator + "P1" + Separator + view.getGridSize() + Separator + gridType + Separator + gridContent);
			getCout().flush();

		}
		return null;
	}
	
	private void receiveGame() {
        getCout().println(userName.getText() + Separator + "P2"); 
		getCout().flush();

	}
	
	private void newGame() {
		   getCout().println(userName.getText() + Separator + "P5"); 
			getCout().flush();
	}
	
	private void startGame() {
		 getCout().println(userName.getText() + Separator + "P6"); 
			getCout().flush();
	}
	
	
    void getGui() {
		addComponentsToPane(frame.getContentPane());
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

	public void beginConnection() {
		if(tries >= 1) {
			tries = -1;
			return;
		}
		     		
     		try {
				setSocket(new Socket(serverName.getText(), Integer.parseInt(portNumber.getText())));
	            ThreadClient threadClient = new ThreadClient(socket,this);
	            setThreadClient(threadClient);
			} catch (ConnectException e1) {
				try {
					//retry's the connection to the server!
					status.setText("No server found!");
				  	Thread.sleep(50);
				  	tries++;
				  	beginConnection();
					
				} catch (InterruptedException e) {
					e.printStackTrace();
					
				}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
        	SwingWorker<Void,Void> worker = new SwingWorker<Void,Void>(){ //create a new thread
        	
        		@Override
    			protected Void doInBackground() throws Exception {
            PrintWriter cout = new PrintWriter(socket.getOutputStream(), true);
            setCout(cout);
            new Thread(getThreadClient()).start(); // start thread to receive message
            sendGame.addActionListener(e -> sendGame());
            End.addActionListener(e -> endGame());
            receiveGame.addActionListener(e -> receiveGame());
            sendData.addActionListener(e -> sendData());
            newGame.addActionListener(e -> newGame());
            Play.addActionListener(e -> startGame());
            cout.println(userName.getText() + Separator);
    		getCout().flush();

			return null;
           
        	}

			

				
    	};
    	worker.execute();
    }
	
    
    
    

    public void addComponentsToPane(Container pane) {
		if (RIGHT_TO_LEFT) {
			pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		}

		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
	
		user = new JButton("User: ");
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		user.setBorderPainted(false);
		user.setFocusPainted(false);
		user.setContentAreaFilled(false);
		pane.add(user, c);

		userName = new JTextField("Paulo", 7);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2.5;
		c.gridx = 1;
		c.gridy = 0;
		pane.add(userName, c);

		server = new JButton("Server");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2.5;
		c.gridx = 2;
		c.gridy = 0;
		server.setBorderPainted(false);
		server.setFocusPainted(false);
		server.setContentAreaFilled(false);
		pane.add(server, c);

		serverName = new JTextField("localhost", 7);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2.5;
		c.gridx = 3;
		c.gridy = 0;
		pane.add(serverName, c);

		port = new JButton("Port");
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		port.setBorderPainted(false);
		port.setFocusPainted(false);
		port.setContentAreaFilled(false);
		pane.add(port, c);

		portNumber = new JTextField("147", 7);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2.5;
		c.gridx = 1;
		c.gridy = 1;
		pane.add(portNumber, c);

		Connect = new JButton("Connect");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2.5;
		c.gridx = 2;
		c.gridy = 1;

		pane.add(Connect, c);

		End = new JButton("End");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2.5;
		c.gridx = 3;
		c.gridy = 1;
		pane.add(End, c);

		newGame = new JButton("New game");
	
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		pane.add(newGame, c);

		sendGame = new JButton("Send game");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2.5;
		c.gridx = 1;
		c.gridy = 2;
		pane.add(sendGame, c);

		receiveGame = new JButton("Receive game");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2.5;
		c.gridx = 2;
		c.gridy = 2;

		pane.add(receiveGame, c);

		sendData = new JButton("Send data");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2.5;
		c.gridx = 3;
		c.gridy = 2;
		pane.add(sendData, c);

		Play = new JButton("Play");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2.5;
		c.gridx = 4;
		c.gridy = 2;
		pane.add(Play, c);

		status = new JLabel("Connect to a server! ");
		c.fill = GridBagConstraints.CENTER;
		c.weightx = 2.5;
		c.gridx = 0;
		c.gridy = 3;
		status.setHorizontalAlignment(JLabel.CENTER);
		Connect.addActionListener(e -> beginConnection());
		pane.setVisible(true);
		pane.add(status, c);
		

    }

	

	
	
    }

