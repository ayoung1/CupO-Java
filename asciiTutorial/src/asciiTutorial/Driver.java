package asciiTutorial;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import asciiPanel.AsciiPanel;


public class Driver  extends JFrame implements KeyListener{

	//for serializing if needed
	private static final long serialVersionUID = 1060623638149583738L;
	private static AsciiPanel terminal;
	private static Screen screen;
	
	
	public Driver(){
		super();
		
		terminal = new AsciiPanel(50, 25); //displays as (i, 2j)
		/*//tutorial 1 code
		terminal.write("rl tutorial", 1, 1); //x y coordinates to write
		terminal.write("this is the hook", 4, 1);
		
		screen = new StartScreen();
		screen.displayOutput(terminal);
		this.add(terminal);
		this.addKeyListener(this);
		this.setResizable(false);
		this.pack();
		*/ //end tutorial 1 code
		
        terminal = new AsciiPanel();
        add(terminal);
        pack();
        screen = new StartScreen(this);
        addKeyListener(this);
        repaint();
	}
	
	public static void main(String[] args)
	{
		Driver app = new Driver();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		screen = screen.respondToUserInput(arg0);
		if(screen == null)
			System.exit(1);
        repaint();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
	
	
    public void repaint(){
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }
}
