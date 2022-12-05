
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class Server {
	JTextArea console = new JTextArea(12,25);
	JLabel connect;
	JLabel label = new JLabel("dw", JLabel.CENTER);
	JFrame frame;
    JButton start;
    JButton results;
    JButton end;
    JTextField portField = new JTextField(3);
    JCheckBox j = new JCheckBox();
    Socket socket;
	JFrame f;
    JButton b, b1;
	JTextField b2;
    JLabel l;
    JLabel finalize;  
	JButton sendMessage = new JButton();
    Server server;
    JPanel p = new JPanel();
    NumPuz view;
    ArrayList<Config> gridSettings = new ArrayList<>();

    

    
    public Server(NumPuz view) {
	this.view = view;
	}

	public void beginServer() {
    	server = this;
        ArrayList<Socket> clients = new ArrayList<>();
        HashMap<Socket, String> clientNameList = new HashMap<Socket, String>();
       
        SwingWorker<Void,Void> worker = new SwingWorker<Void,Void>(){ 

    		@Override
			protected Void doInBackground() throws Exception {
        
        try {
        	ServerSocket serversocket = new ServerSocket(Integer.parseInt(b2.getText()));
            console.append("Server is starting \n \n");
           
            while (true) {
                Socket socket = serversocket.accept();
                clients.add(socket);
                ThreadServer ThreadServer = new ThreadServer(socket, clients, clientNameList,server);
                sendMessage.addActionListener(e -> ThreadServer.sendMessage("Paulo#P3"));
                results.addActionListener(e -> ThreadServer.showMessageToAllClients(socket, ""));
                b.addActionListener(e -> endConnection(serversocket,f));
                ThreadServer.start();
            }
            
            
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
		return null;
    }
	
        };
		worker.execute();		
	}
	
	
	private void endConnection(ServerSocket serversocket, JFrame f2) {
		try {
			System.out.println("closed");
			serversocket.close();
			f2.dispose();
		} catch (Exception e) {
        	JOptionPane.showMessageDialog(null,"Closing the server!");

		}
		
		
	}
    
   public void initializeGUI(){
		f = new JFrame("panel");
	  
       // Creating a label to display text
       JLabel l2 = new JLabel("Port");  
       connect = new JLabel("Initiate a connection..");  
       finalize = new JLabel("Finalize the game connection!!!");  
       results = new JButton("Results");
       l2.setBounds(50,100, 100,40);  
       // Creating a new buttons
       b = new JButton("End");
       b1 = new JButton("Start");

       b2 = new JTextField(6);
       // Creating a panel to add buttons
       console.setBounds(10,30, 200,200);
       console.setEditable(false);
       JScrollPane scrollPane = new JScrollPane(console);
       scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
       
       p.add(b);
       p.add(b1);
       p.add(results);
       p.add(b2);
       
       p.add(l2);
       p.add(connect);
       p.add(finalize);
       p.add(j);
       // setbackground of panel
       p.setBackground(Color.red);
       p.add(scrollPane);

       // Adding panel to frame
       f.add(p);

       // Setting the size of frame
       f.setSize(350, 350);
       f.setVisible(true);

       b1.addActionListener(e -> beginServer());

}
   public void saveGridSetting(String size,String type,String text) {
	 
	   
	   if(gridSettings.size() == 1) {
		   gridSettings.removeAll(gridSettings);
	   }
	   Config grid = new Config(size,type,text);
	   gridSettings.add(grid);	
   }
   
   public String getGridSetting(String clientUsername) {
 	   String gridContents = "";
 	 
 	   if(gridSettings.size() == 0) {
		   console.append("\n" + "No game configuration has been set");
		   console.append("\n" + "sending a random one instead!");
		   return "Paulo#P2#3#Text#ourdftqby";
	   }
 	  
	   gridContents = clientUsername + '#' + "P2" + '#' + gridSettings.get(0).size + '#' + gridSettings.get(0).type + '#' + gridSettings.get(0).text;
	  return gridContents;
   }


   
   
   
}
