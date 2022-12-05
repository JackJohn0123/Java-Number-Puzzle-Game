import javax.swing.JFrame;
import javax.swing.UIManager;

public class Main {
public static void main(String[] args) {
		
		
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
					NumPuz frame = new NumPuz("GridLayoutDemo");
					NumPuzModel model = new NumPuzModel(frame); 
					NumPuzController controls = new NumPuzController(frame,model);					
					SplashScreen splash = new SplashScreen();
					splash.Splash();
					model.changeGrid(3,controls);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.addComponentsToPane(frame.getContentPane());
					frame.setSize(1360,900);
					frame.registerListener(controls);
					frame.setVisible(true); 
					frame.setResizable(true);
				
			}
		}
		);
	}
	


}
