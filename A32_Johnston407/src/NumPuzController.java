import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;


public class NumPuzController implements ActionListener {
	private NumPuz view;
	private NumPuzModel model;
	
	
	public NumPuzController(NumPuz View, NumPuzModel Model) {
		this.view = View;
		this.model = Model;
	}
	 
	@Override
	/* The action listener for all our components are placed here */
	public void actionPerformed(ActionEvent e) 
	
		{
			
			if(e.getActionCommand().startsWith("Grid")) {
				if(view.isModeSelectedPlay()) {
				model.moveTiles(e.getActionCommand(),view);
				String Coordinates = model.returnCoordinates();
				view.setTextField(Coordinates);
				
				if(view.getTemplateOptions() == "Numbers") {
					model.calculateCorrectNumberTiles();
				}
				if(view.getTemplateOptions() == "Text") {
					model.calculateCorrectTextTiles();
				}
				}
			
			}

			if(e.getSource() == view.getBsave()) {

				if(!view.isNull(view.fileDirectory.showSaveDialog(null))){
				 	String filename = view.fileDirectory.getSelectedFile().getName();
				 	String path = view.fileDirectory.getCurrentDirectory().getAbsolutePath() + "/";
				 	if(filename.indexOf(".txt") !=-1? true: false) {

				 		view.write(filename,path);
				 	}
				 	else {
				 		view.write(filename + ".txt",path);
				 	}
					}

			}

			if(e.getSource() == view.getBfinish()) {
				if(view.getTemplateOptions() == "Text" && model.isFinishText()) {
					double points = model.calculatePointsText(true);
					view.setPoints(points);
					view.startTimer(false);
					model.winScreen();
				}
				if (view.getTemplateOptions() == "Numbers" && model.isFinishNumbers()) {
					double points = model.calculatePointsText(false);
					view.setPoints(points);
					view.startTimer(false);
					model.winScreen();
			}
			}
			
			

			if(e.getSource() == view.getBsetNames()) {
				String setNames = view.getSetNameTextBox();
				
				view.changeGrid(view.gridSize(),this);
				view.newGridText(setNames);
				view.getComboBoxGrid().setSelectedIndex(1);
				
			}

			if(e.getSource() == view.getBload()) {
				
				if(!view.isNull(view.fileDirectory.showOpenDialog(null))){
				 	String filename = view.fileDirectory.getSelectedFile().getName();
				 	String path = view.fileDirectory.getCurrentDirectory().getAbsolutePath() + "/";
				 	if(filename.indexOf(".txt") !=-1? true: false) {
				 		view.read(filename,path);
				 		
				 	}
				 	else {
				 		view.read(filename + ".txt",path);
				 	}
					}
			
				view.startTimer(false);
				view.hasGameStarted();
				view.design.doClick();
				view.play.doClick();
			}
			if(e.getSource() == view.getBrandom()) {
				view.setAllVisible();
				view.randomize();
			}
			
			if(e.getSource() == view.getBtemplate()) {

				String option = view.comboBoxGrid.getSelectedItem().toString();
				if(option == "Numbers") {
					view.changeGrid(view.gridSize(),this);
				}
				if(option == "Text") {
					view.newGridText("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
					view.setAllVisible();
					view.randomize();
				}
				view.gameBoard.revalidate();
			}


			if(e.getSource() == view.getPlay()) {
				view.Bfinish.setEnabled(true);
				view.Bshow.setEnabled(false);
				view.Bhide.setEnabled(false);
				view.Brandom.setEnabled(false);
				view.Btemplate.setEnabled(false);
				view.applyButton.setEnabled(false);
				view.saveMenu.setEnabled(false);
				view.getBload().setEnabled(false);
				view.getBsave().setEnabled(false);
				view.getComboBoxSize().setEnabled(false);
				view.getComboBoxGrid().setEnabled(false);
				view.startTimer(true);
				
				
				if(view.hasGameStarted()) {
					model.start(view);
				}
				
				else {
				view.startGame();
				model.start(view);
				}
				
				if(view.getTemplateOptions() == "Numbers") {
					model.calculateCorrectNumberTiles();
				}
				if(view.getTemplateOptions() == "Text") {
					model.calculateCorrectTextTiles();
				}
				
				
				
			}
			if(e.getSource() == view.getApplyButton()) {
				int size = (int) view.comboBoxSize.getSelectedItem();
				view.setGridSize(size);
				view.gameBoard.removeAll();
				view.gameBoard.repaint();
				view.changeGrid(size,this);
				view.gameBoard.revalidate();
				view.textField();
				view.design.doClick();
				view.getComboBoxGrid().setSelectedIndex(0);
			}

			if(e.getSource() == view.getColors()) {
				view.colorGridLayout();
		
			}
			if(e.getSource() == view.getAboutItem()) {
						view.getHelp();
			}
			if(e.getSource() == view.getJoinServer()) {
				Client client = new Client(view,this);
				client.getGui();
			}
			if(e.getSource() == view.getHostServer()) {
			      Server server = new Server(view);
			      server.initializeGUI();
			}
			if(e.getSource() == view.getDesign()) {

				view.Bfinish.setEnabled(false);
				view.Bshow.setEnabled(true);
				view.Bhide.setEnabled(true);
				view.Brandom.setEnabled(true);
				view.Btemplate.setEnabled(true);
				view.startTimer(false);
				view.setPoints(0);
				view.applyButton.setEnabled(true);
				view.saveMenu.setEnabled(true);
				view.getBload().setEnabled(true);
				view.getBsave().setEnabled(true);
				view.getComboBoxSize().setEnabled(true);
				view.getComboBoxGrid().setEnabled(true);
				view.setColorDefault();
			}
		
			if(e.getSource() == view.getDesign()) {

				view.Bfinish.setEnabled(false);
				view.Bshow.setEnabled(true);
				view.Bhide.setEnabled(true);
				view.Brandom.setEnabled(true);
				view.Btemplate.setEnabled(true);
			}
		
			if(e.getSource() == view.getExitMenu()) {
				
				view.dispose();
		
			}
			if(e.getSource() == view.getOpenMenu()) {
				view.setGridSize(3);
				view.gameBoard.removeAll();
				view.gameBoard.repaint();
				view.changeGrid(3,this);
				view.gameBoard.revalidate();
				view.textField();
				view.design.doClick();
				view.getComboBoxGrid().setSelectedIndex(0);
				view.message();
			}
			
			
		}
	}
	
