package triviaScreens;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import asciiPanel.AsciiPanel;

public class StartScreen implements Screen{
	private JFrame display;
	
	public StartScreen(JFrame d)
	{
		display = d;
	}
	
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.write("Trivia Maze", 1, 1);
		terminal.write("by", 1, 2);
		terminal.write("A Cup 'o Java", 1, 3);
		terminal.write("Bryce Fenske, Trae Rawls, Aaron Young", 1, 4);
		terminal.writeCenter("-- press [Enter] to start --", 22);
		
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		return key.getKeyChar() == '\n' ? new MainMenuScreen(display): this;
	}
	
}
