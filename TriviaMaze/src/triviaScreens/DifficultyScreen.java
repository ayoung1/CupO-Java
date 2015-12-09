package triviaScreens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import maze.Maze;

public class DifficultyScreen implements Screen{
	private Screen parent;
	private String[] difficulty = {"regular", "hard"}; //maze.getDifficulties()?
	private int menuNum = 0;
	private boolean selected = false;
	
	public DifficultyScreen(Screen par)
	{
		parent = par;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		if(!selected)
		{
			terminal.write("Select Difficulty:", 1, 1);
			for(int i = 0; i < difficulty.length; i++)
			{
				if(menuNum == i)
					terminal.write("*", 1, i + 2);
				terminal.write(difficulty[i], 2, i + 2);
			}
		}else
		{
			terminal.clear();
			terminal.write("Controls here", 1, 1);
		}
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(!selected)
		{
			if(key.getKeyCode() == KeyEvent.VK_ENTER)
			{
				selected = true;
				return this;
			}
			else if(key.getKeyCode() == KeyEvent.VK_W)
			{
				if(menuNum == 0)
					menuNum = difficulty.length - 1;
				else
					menuNum--;
			}
			else if(key.getKeyCode() == KeyEvent.VK_S)
			{
				if(menuNum == difficulty.length - 1)
					menuNum = 0;
				else
					menuNum++;
			}
			// TODO Auto-generated method stub
			return this;
		}
		return new GameScreen(this, new Maze(difficulty[menuNum]));
	}

}
