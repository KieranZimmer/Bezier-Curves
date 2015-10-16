package bezierCurves;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

//Class used to 
public class MainPanel extends JFrame implements WindowListener{
	private ControlPanel con;
	
	public MainPanel() {
		Container content = getContentPane();
		con = new ControlPanel(this); //creates a JPanel
		content.add(con, "Center"); //add JPanel to Container
		addWindowListener(this); //adds a listener to Container to check if it is altered
		
		pack();
		setVisible(true);
		setResizable(false);		
	}

	//stops the game when the window is closed
	public void windowClosing(WindowEvent e) {
		con.stopGame();
	}

	//declared, but unfilled methods from the WindowListener interface
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
}
