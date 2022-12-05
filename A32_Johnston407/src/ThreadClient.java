
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JOptionPane;

/**
 * Thread for clients
 */
public class ThreadClient implements Runnable {

    private Socket socket;
    private BufferedReader cin;
    Client client;
	String[] messageString;

    
    
    public ThreadClient(Socket socket, Client client) throws IOException {
        this.socket = socket;
        this.cin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.client = client;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = cin.readLine();
            	messageString = message.split("#");
            	String protocol = messageString[1];
            	
            	
                if(protocol.contains("P0")) {
                	socket.close();
               
                }
                if(protocol.contains("P1")) {

                	
                }
                if(protocol.contains("P2")) {
                	if(messageString[2].equals("Numbers")) {
                	client.view.setGridSize(Integer.parseInt(messageString[2]));
                	client.view.gameBoard.removeAll();
                	client.view.gameBoard.repaint();
                	client.view.changeGrid(Integer.parseInt(messageString[2]),client.controller);
    				client.view.gameBoard.revalidate();
    				client.view.textField();
    				client.view.design.doClick();
    				client.view.getComboBoxGrid().setSelectedIndex(0);
                }
                	
                	else{
                		String s = messageString[4];
                		client.view.changeGrid(Integer.parseInt(messageString[2]),client.controller);
                		s = s.replace(",","");
                		client.view.newGridText(s);
                		client.view.getComboBoxGrid().setSelectedIndex(1);
                	}
                	
                	continue;
                	
               
                }
            
                if(protocol.contains("P3")) {
                	continue;
               
                }
                if(protocol.contains("P4")) {
                	client.sendData();
                	continue;
               
                }
                if(protocol.contains("P6")) {
                	client.view.play.doClick();
                	client.frame.setState(Frame.ICONIFIED);
                	continue;
               
                }
            
               
            }
        } catch (SocketException e) {
        	JOptionPane.showMessageDialog(null,client.userName.getText() + " have disconnected");
        	
        } catch (IOException e) {
			
			e.printStackTrace();
		} finally {
            try {
                cin.close();
            } catch (Exception exception) {
                System.out.println(exception);
            }
        }
    }
    
    public void sendData() {
    	
    	
    }


    
}
