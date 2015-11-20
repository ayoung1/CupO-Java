package asciiTutorial;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class LoadScreen implements Screen{
	private Screen parent;
	
	public LoadScreen(Screen par)
	{
		parent = par;
	}
	
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.write("Check for serializable items in current save folder, give option to load", 1, 1);
		terminal.write("Loads maze, position, doors, and answered questions", 1, 2);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		// TODO Auto-generated method stub
		return parent;
	}

}
