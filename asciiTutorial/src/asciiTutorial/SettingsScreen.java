package asciiTutorial;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class SettingsScreen implements Screen{
	private Screen parent;
	
	public SettingsScreen(Screen par)
	{
		parent = par;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.write("Rooms (4 min)", 1, 1);
		terminal.write("Categories: (get categories)", 1, 2);
		terminal.write("Paths to exit", 1, 3);
		terminal.write("NOTE: probably scrapping this", 1, 4);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		// TODO Auto-generated method stub
		return parent;
	}

}
