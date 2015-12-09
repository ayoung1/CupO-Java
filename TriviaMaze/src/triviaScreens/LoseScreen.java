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
		terminal.write("You lost!", 1, 1);
		terminal.write("No routes to finish remain", 1, 2);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		// TODO Auto-generated method stub
			driver.Driver.exit(); //driver null?
		return null;
	}
}
