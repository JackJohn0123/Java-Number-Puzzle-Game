import java.awt.Color;
import java.awt.GridLayout;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.View;





public class NumPuzModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	NumPuz frame;
	int r;
	String Coordinates = "";
	
	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public int getrNeighbor() {
		return rNeighbor;
	}

	public void setrNeighbor(int rNeighbor) {
		this.rNeighbor = rNeighbor;
	}

	public int getcNeighbor() {
		return cNeighbor;
	}

	public void setcNeighbor(int cNeighbor) {
		this.cNeighbor = cNeighbor;
	}
	int c;
	int rNeighbor;
	int cNeighbor;
	
	public NumPuzModel(NumPuz View) {
        super();
        this.frame = View;
    }
	
	public void start(NumPuz view) {
		
	}
	/* move tiles into the neighbors position */
	public boolean moveTiles(String coordinates, NumPuz view) {
		coordinates = coordinates.replaceAll("[^\\d-.]", "");
		String[] parts = coordinates.split("-");
		
		String Srow = parts[0]; 
		String Sheight = parts[1];
		
		int row = Integer.parseInt(Srow);
		int height = Integer.parseInt(Sheight);
		
		return checkEmpty(row, height, -1, 0) || checkEmpty(row, height, 1, 0)
	            || checkEmpty(row, height, 0, -1) || checkEmpty(row, height, 0, 1);
		
	}
	 private boolean checkEmpty(int r, int c, int rdelta, int cdelta) {
	        int rNeighbor = r + rdelta;
	        int cNeighbor = c + cdelta;
	        if (isLegalRowCol(rNeighbor, cNeighbor)
	                  && frame.buttons[rNeighbor][cNeighbor].getText() == ".") {
	        	setC(c);
	    		setcNeighbor(cNeighbor);
	    		setR(r);
	    		setrNeighbor(rNeighbor);
	        	exchangeTiles(r, c, rNeighbor, cNeighbor);
	        	return true;
	        }
	        return false;
	    }
	public String returnCoordinates() {
		Coordinates = "row " + getR() + " column " + getC() + " has been moved to " + getcNeighbor() + "-" + getrNeighbor();
		return Coordinates;
	}
	/*  exchanges the text of the grid */
	private void exchangeTiles(int r, int c, int rNeighbor, int cNeighbor) {
			frame.buttons[rNeighbor][cNeighbor].setText(frame.buttons[r][c].getText());
			frame.buttons[r][c].setText(".");
			frame.buttons[r][c].setVisible(false);
			frame.buttons[rNeighbor][cNeighbor].setVisible(true);
	}
	/* checks if the tile is out-of-bounds */
	private boolean isLegalRowCol(int rNeighbor, int cNeighbor) {
		if(rNeighbor>=0 && rNeighbor<frame.getGridSize() && cNeighbor>=0 && cNeighbor<frame.getGridSize()) {

			return true;
		}
		return false;
		
	}
	/* updates the grid */
	public void changeGrid(int i, NumPuzController controller) {
		frame.gameBoard.setLayout(new GridLayout(frame.getGridSize(),frame.getGridSize()));
		for(int h = 0; h < frame.getGridSize();h++) 
			for(int r = 0; r < frame.getGridSize();r++) {
				frame.buttons[r][h] = new JButton("row " + r + " height " + h );	
				frame.gameBoard.add(frame.buttons[r][h]);
				frame.buttons[r][h].addActionListener(controller);
				frame.buttons[r][h].setActionCommand("Grid"+ " " + r +"-"+ h);
			}	
	}
	/* checks if the current grid matches the final grid */
	public boolean isFinishText() {
		String buttonText = "";
		for(int h = 0; h < 3;h++) 
			for(int r = 0; r < 3;r++) {
				
				if(frame.buttons[h][r].getText() == ".") {
				}
				else {					
					buttonText = frame.buttons[h][r].getText();
					if(buttonText != frame.textGridArray[h][r]) {
					return false;
					}
				}
			}			
		return true;	
	}
	/* check if the number grid matches the final grid  */
	public boolean isFinishNumbers() {
		String buttonText = "";
		for(int h = 0; h < frame.getGridSize();h++) 
			for(int r = 0; r < frame.getGridSize();r++) {
				if(frame.buttons[r][h].getText() == ".") {
					
				}
				else {
					buttonText = frame.buttons[r][h].getText().replaceAll("[^\\d-.]", "");
					String row = buttonText.substring(0, (buttonText.length()/2));
					String height = buttonText.substring((buttonText.length()/2));
					
					if(Integer.parseInt(row) != r || Integer.parseInt(height) != h) {
						return false;
					}	
				}
			}			
		return true;	
	}
	/* calculate the amount of points ther user accumulates */
	public double calculatePointsText(boolean choice) {
	int totalGreenTiles = 0;
	if(choice) {
	totalGreenTiles = calculateCorrectTextTiles();
	double basePoints = 2000;
	int maximumTime = 7800;
	double denominator = frame.getSeconds() / maximumTime;
	double points = basePoints / Math.pow(basePoints, denominator);
	totalGreenTiles = calculateGreenTiles();
	return (Math.round(points) * totalGreenTiles *  frame.gridSize - 2) ;
	}
	if(!choice) {
		totalGreenTiles = calculateCorrectNumberTiles();
		double basePoints = 2000;
		int maximumTime = 7800;
		double denominator = frame.getSeconds() / maximumTime;
		double points = basePoints / Math.pow(basePoints, denominator);
		return Math.round(points) * totalGreenTiles * frame.gridSize - 2 ;
		
		}
	return -1;
	}
	/* win screen */
	public void winScreen() {
		JFrame win= new JFrame("Simple GUI");
		win.add(new JLabel(new ImageIcon("gamewinner.png")));
		win.setSize(450,450);
		win.setVisible(true);
		frame.textField.setText("You Win");
	}
	/* error screen */
	public void errorScreen() {
		JFrame win= new JFrame("Simple GUI");
		win.add(new JLabel(new ImageIcon("gamewinner.png")));
		win.setSize(450,450);
		win.setVisible(true);
		frame.textField.setText("Error Detected");
		
	}
	/*  calculate the number of green tiles (correct) in the grid */
	public int calculateCorrectNumberTiles() {
		if(frame.getTemplateOptions() == "Numbers") {
			
			String buttonText = "";
			for(int h = 0; h < frame.getGridSize();h++) 
				for(int r = 0; r < frame.getGridSize();r++) {
					if(frame.buttons[r][h].getText() == ".") {
						
					}
					else {
						buttonText = frame.buttons[r][h].getText().replaceAll("[^\\d-.]", "");
						String row = buttonText.substring(0, (buttonText.length()/2));
						String height = buttonText.substring((buttonText.length()/2));
						
						if(Integer.parseInt(row) == r && Integer.parseInt(height) == h) {
							frame.buttons[r][h].setBackground(Color.green);
						}	else {
								frame.buttons[r][h].setBackground(frame.gridColor());
					
						}
					}
				}			
			
		}
		int greenTiles = calculateGreenTiles();
		return greenTiles;
	}

	public int calculateCorrectTextTiles() {
		String buttonText = "";
	
		for(int h = 0; h < frame.getGridSize();h++) 
			for(int r = 0; r < frame.getGridSize();r++) {
				
				if(frame.buttons[h][r].getText() == ".") {
					
				}
				else {					
					buttonText = frame.buttons[h][r].getText();
					
					if(buttonText == frame.textGridArray[h][r]) {
						frame.buttons[h][r].setBackground(Color.green);
					}
					else {
						frame.buttons[h][r].setBackground(frame.gridColor());
					}
					
				}
			}	
		int greenTiles = calculateGreenTiles();

		return greenTiles;
	}
		
	


	
int calculateGreenTiles(){
	int greenTiles = 0;
	for(int h = 0; h < frame.getGridSize();h++) 
		for(int r = 0; r < frame.getGridSize();r++) {
			if(frame.buttons[h][r].getBackground() == Color.green) {
				greenTiles++;
			
		}
	
}
	
return greenTiles;
}

}

	   
	
	
