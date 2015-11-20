package asciiTutorial;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class GameScreen implements Screen{

	private Screen parent;
	
	public GameScreen(Screen par)
	{
		parent = par;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		// TODO Auto-generated method stub
		terminal.write("Pull questions, "
				+ "Display maze, current question, have option to save", 1, 1);
		
		terminal.write("(saves maze, position, doors, and answered questions)", 1, 2);
		terminal.write("include overloaded constructor for game state", 1, 3);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		// TODO Auto-generated method stub
		return parent;
	}

}
