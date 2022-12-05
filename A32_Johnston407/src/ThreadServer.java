import java.awt.Color;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Thread for Server
 */
public class ThreadServer extends Thread {

    private Socket socket;
    private ArrayList<Socket> clients;
    private HashMap<Socket, String> clientNameList;
    String[] messageString;
    Server server;
    BufferedWriter output;
    
    public BufferedWriter getOutput() {
		return output;
	}

	public void setOutput(BufferedWriter output) {
		this.output = output;
	}

	public ThreadServer(Socket socket, ArrayList<Socket> clients, HashMap<Socket, String> clientNameList, Server server) {
        this.socket = socket;
        this.clients = clients;
        this.clientNameList = clientNameList;
        this.server = server;
    }

    @Override
    public void run() {
    	try {
    
    		String outputString;
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));            	
	        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	        setOutput(output);
	        
	        
	    	if(server.j.isSelected()) {
	    			output.write("ERR" + '#' +"P0");
					output.newLine(); //HERE!!!!!!
					output.flush();
					socket.close();
					return;
	    	}
	       
            while (true) {
            	
                outputString = input.readLine();
                
                if (!clientNameList.containsKey(socket)) {
                    messageString = outputString.split("#");
                    clientNameList.put(socket, messageString[0]);
                    server.console.append("\n" + messageString[0] + " has connected \n ");
                	server.p.setBackground(Color.green);
                               
                } 
                                
                else {
                	messageString = outputString.split("#");
					
                    String clientUsername = messageString[0];
                    String protocolID = messageString[1];
                  
                    if(protocolID.equals("P0")) {
                    	output.write(clientUsername + '#' +"P0");
                    	output.newLine(); 
     					output.flush();
                    	clientNameList.remove(socket,clientUsername);
                    	server.console.append("\n" + clientUsername + " has left \n");
                    }
                    
                    if(protocolID.equals("P1")) {

     					server.console.append("\n" + clientUsername + " has sent a game configuration");
     					server.console.append("\n" + clientUsername + " configuration is now the default configuration");
     				
     					String gridSize = messageString[2];
     					String gridType = messageString[3];
     					String gridText = messageString[4];
     	                String[] split1 = gridText.split("#");
     	                server.saveGridSetting(gridSize,gridType,gridText);

     	                
     				}	
     				if(protocolID.equals("P2")) {
     					server.console.append("\n" + "Sending custom game configuration");
     					String gridContents = server.getGridSetting(clientUsername);
     					output.write(gridContents);
     					output.newLine(); //HERE!!!!!!
     					output.flush();
     					
     				}
     				if(protocolID.equals("P3")) {
     					//server replying to protocols!
     					
     				}
     				if(protocolID.equals("P4")) {
     					String points = messageString[2];
     					String Time = messageString[3];
     					server.console.append("\n" + clientUsername + ": " + points + " " + Time + " "  );
     							
     				}
     			
     				if(protocolID.equals("P5")) {
     					server.console.append("\n" + clientUsername + " has been given a random game configuration");

     					String randomString = "";
     					Random random = new Random();
     					
     					int randomSize = random.nextInt(7 - 3) + 3;  
     					
     					String[] randomType = {"Text", "Numbers"};
     					String type = randomType[random.nextInt(randomType.length)];
     					
     					for(int i = 0; i < randomSize*randomSize; i++) {
     						char c = (char)(random.nextInt(26) + 'a');
     						randomString = randomString + c;
     						
     					}
     					
     					output.write(clientUsername + '#' + "P2" + '#' + randomSize + '#' + type + '#' + randomString);
     					output.newLine(); //HERE!!!!!!
     					output.flush();
     					
     				}

     				if(protocolID.equals("P6")) {
     					server.console.append("\n" + clientUsername + " has started the game!");
     					output.write(clientUsername + '#' + "P6");
     					output.newLine(); //HERE!!!!!!
     					output.flush();
     							
     				}
     				
                }

            
            }
        } catch (IOException e) {
            clients.remove(socket);
            clientNameList.remove(socket);
        }
    	catch (NullPointerException e) {
    
    }
    }
    public void sendMessage(String message) {
    	this.showMessageToAllClients(socket,message);
    	
    }
    
    
    public void showMessageToAllClients(Socket sender, String outputString) {
    	if(clients.size() == 0) {
        	return;
        }
    	        
        for(int i = 0; i < clients.size(); i++) {
            socket = clients.get(i);
            try {
            	String userName = clientNameList.get(socket);
            	getOutput().write(userName + '#' + "P4");
            	getOutput().newLine(); //HERE!!!!!!
            	getOutput().flush();
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
      
    }   
         
    
    
    
}   
    

