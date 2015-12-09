package triviaScreens;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import asciiPanel.AsciiPanel;

public class MainMenuScreen implements Screen{

	private boolean canLoadGame = false;
	private int menuNum = 0;
	private JFrame display;
	
	private String[] menu = {"Start Game", "Load Game", "Admin Login", "Exit"};
	private Screen[] screens = {new DifficultyScreen(this), new LoadScreen(this),  
			new AdminScreen(this), null};
	private Screen currentScreen = screens[0];	
	//make each of these objects/states/screens?
	
	public MainMenuScreen(JFrame d)
	{
		display = d;
		//check for serialized game states
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		for(int i = 0; i < menu.length; i++)
		{
			if(menuNum == i)
				terminal.write("*", 1, i + 1);
			terminal.write(menu[i], 2, i + 1);
		}
		terminal.writeCenter("-- Press [Enter] to select, [w] [s] to move --", 22);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_ENTER)
			return currentScreen;
		else if(key.getKeyCode() == KeyEvent.VK_W)
		{
			if(menuNum == 0)
				menuNum = menu.length - 1;
			else
				menuNum--;
		}
		else if(key.getKeyCode() == KeyEvent.VK_S)
		{
			if(menuNum == menu.length - 1)
				menuNum = 0;
			else
				menuNum++;
		}
		currentScreen = screens[menuNum];
		return this;
	}
}
