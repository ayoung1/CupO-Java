package asciiTutorial;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import asciiPanel.AsciiPanel;

public class ExitScreen implements Screen{

	private JFrame display;
	private Screen parent;
	
	public ExitScreen(Screen par, JFrame j)
	{
		parent = par;
		display = j;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		display.dispose();
		return null;
	}

}
