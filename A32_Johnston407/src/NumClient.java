import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class NumClient {
	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;
	JLabel connect, status;
	JLabel label = new JLabel("dw", JLabel.CENTER);
	JFrame frame;
	JButton start;
	JButton results;
	JButton end;
	JTextField portField = new JTextField(3);
	JFrame f;
	JButton b, b1;
	JTextField b2, userName, serverName, portNumber;
	JLabel l;
	JButton user, server, port, Connect, End, newGame, sendGame, receiveGame, sendData, Play;
	int maxTries = 5;
	String why = "";
	int count = 0;
	String client_id = "";
	char Separator = '#';
	String Protocol_Id = "";
	DataInputStream is;

	public DataInputStream getIs() {
		return is;
	}

	public void setIs(DataInputStream is) {
		this.is = is;
	}

	public void setTest(String test) {
		this.why = test;
	}

	public String getTest() {
		return why;
	}

	public DataOutputStream getOs() {
		return os;
	}

	public void setOs(DataOutputStream os) {
		this.os = os;
	}

	DataOutputStream os;
	Socket client = null;

	public Socket getClient() {
		return client;
	}

	public void setClient(Socket client) {
		this.client = client;
	}

	public void clientProcess() {
		
		
		SwingWorker<Void,Void> worker = new SwingWorker<Void,Void>(){
			@Override
			
			protected Void doInBackground() throws Exception {
				count = 0;
			try {
			
			if(client == null && count < maxTries) {
				client = new Socket(serverName.getText(),Integer.parseInt(portNumber.getText()));
				setClient(client);
			}
			
			if(count >= 4 && client == null) {
				count = 0;
				status.setText("No server could be found!");
			
			}
		
			is = new DataInputStream(getClient().getInputStream());
			setIs(is);
			os = new DataOutputStream(getClient().getOutputStream());
			setOs(os);	
	
			getOs().writeUTF(userName.getText() + Separator + "P1");
			getOs().flush();
	
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			status.setText("No server found!,retrying");
			    try {
					Thread.sleep(1000);
					status.setText("retrying!!");
					System.out.println(count);
					count++;
					
				} catch (InterruptedException e1) {
					
					
				}
			
		}
		 catch (IOException e) {
			
		}
		
		return null;
		
	}
		};
		worker.execute();
		
	}

	public void initializeGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("GridBagLayoutDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set up the content pane.
		addComponentsToPane(frame.getContentPane());

		// Display the window.
		frame.pack();
		frame.setVisible(true);

	}

	public void addComponentsToPane(Container pane) {
		if (RIGHT_TO_LEFT) {
			pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		}

		JButton button;
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		if (shouldFill) {
			// natural height, maximum width
			c.fill = GridBagConstraints.HORIZONTAL;
		}

		user = new JButton("User: ");
		if (shouldWeightX) {
			c.weightx = 2.5;
		}
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		pane.add(user, c);
		JTextField text;

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

		pane.add(server, c);

		serverName = new JTextField("localhost", 7);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2.5;
		c.gridx = 3;
		c.gridy = 0;
		pane.add(serverName, c);

		port = new JButton("Port");
		if (shouldWeightX) {
			c.weightx = 2.5;
		}
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
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
		if (shouldWeightX) {
			c.weightx = 2.5;
		}
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
		pane.add(status, c);

		Play.addActionListener(e -> clientProcess());
		Connect.addActionListener(e -> clientProcess());
		End.addActionListener(e -> endClientProcess());
		receiveGame.addActionListener(e -> clientProcess());
		sendData.addActionListener(e -> clientProcess());
		newGame.addActionListener(e -> clientProcess());
		sendGame.addActionListener(e -> clientProcess());
	}

	private void endClientProcess() {

		try {
			client = new Socket(serverName.getText(),Integer.parseInt(portNumber.getText()));
			os = new DataOutputStream(getClient().getOutputStream());
			os.writeUTF(userName.getText() + Separator + "P0");
			os.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}