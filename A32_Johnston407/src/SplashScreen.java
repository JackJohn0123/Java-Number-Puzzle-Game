import java.awt.FlowLayout;
import javax.swing.JButton;  
import javax.swing.JFrame;  
import javax.swing.JLabel;  
import javax.swing.*;  
import java.awt.event.*;  

public class SplashScreen {  
    private static JDialog d;  
    public void Splash() {
    	  JFrame f= new JFrame();  
          d = new JDialog(f , "SplashScreen", true);  
          d.setLayout( new FlowLayout() );    

          d.add( new JLabel ("Welcome to my program!" + "\n" + " Created by Jack Johnston"));  
          d.add( new JLabel (new ImageIcon("logo.png")));  

          d.setSize(600,300);  
          d.setVisible(true);  
      
        //  d.dispose(); no idea why this both doesnt work
        //  f.dispose();
    }
} 