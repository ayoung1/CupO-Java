package asciiTutorial;

import javax.swing.JFrame;
import asciiPanel.AsciiPanel;


public class Driver  extends JFrame{

	//for serializing if needed
	private static final long serialVersionUID = 1060623638149583738L;
	private AsciiPanel terminal;
	
	
	public Driver(){
		super();
		terminal = new AsciiPanel(50, 25); //displays as (i, 2j)
		terminal.write("rl tutorial", 1, 1); //x y coordinates to write
		terminal.write("this is the hook", 4, 1);
		this.add(terminal);
		this.setResizable(false);
		this.pack();
	}
	
	public static void main(String[] args)
	{
		Driver app = new Driver();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
	
}
