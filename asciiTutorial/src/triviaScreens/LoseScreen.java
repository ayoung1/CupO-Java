package triviaScreens;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import asciiPanel.AsciiPanel;
import driver.Driver;

public class LoseScreen implements Screen{
	//private JFrame display;
	private Screen parent;
	
	LoseScreen(Screen p)
	{
		parent = p;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.write("You lost! Press [q] to quit or any other key to return to the main menu", 1, 1);
		
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		// TODO Auto-generated method stub
			driver.Driver.exit(); //driver null?
		return null;
	}
}
