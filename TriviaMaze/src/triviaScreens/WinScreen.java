package triviaScreens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class WinScreen implements Screen{

	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.write("Congradulations!", 1, 1);
		terminal.write("You beat the Trivia Maze", 1, 2);
		
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		// TODO Auto-generated method stub
		driver.Driver.exit();
		return null;
	}

}
