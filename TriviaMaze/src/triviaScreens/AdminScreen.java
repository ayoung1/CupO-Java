package triviaScreens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class AdminScreen implements Screen{

	private Screen parent;
	
	public AdminScreen(Screen par)
	{
		parent = par;
	}
	
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		// TODO Auto-generated method stub
		terminal.write("system call to default web browser passing in admin portal for DB", 1, 1);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		// TODO Auto-generated method stub
		return parent;
	}

}
