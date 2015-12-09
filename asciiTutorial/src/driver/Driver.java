//Bryce Fenske

//note: asciiPanel panel can be found here:  https://github.com/trystan/AsciiPanel
//tutorials from the creator: trystans.blogspot.com/2011/08/roguelike-tutorial-01-java-eclipse.html

package driver;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import asciiPanel.AsciiPanel;
import triviaScreens.Screen;
import triviaScreens.StartScreen;


public class Driver  extends JFrame implements KeyListener{

	//for serializing if needed
	private static final long serialVersionUID = 1060623638149583738L;
	private static AsciiPanel terminal;
	private static Screen screen;
	
	
	public Driver(){
		super();
		
		terminal = new AsciiPanel(60, 60); //displays as (i, 2j)
		
		
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
        this.add(terminal);
        this.setResizable(false);
        this.pack();
        screen = new StartScreen(this);
        this.addKeyListener(this);
        this.repaint();
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
    
    public static void exit()
    {
    	System.exit(NORMAL);
    }
    

}