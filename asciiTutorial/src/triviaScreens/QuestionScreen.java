package triviaScreens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import maze.Maze;

public class QuestionScreen implements Screen {

	private Screen parent;
	private Maze maze;
	private int direction;
	private String buffer = "";
	//SQL lite object or strings needed
	
	QuestionScreen(Screen par, Maze m, int dir)
	{
		parent = par;
		maze = m;
		direction = dir;
		
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		// TODO Auto-generated method stub
		terminal.write("This is the hook. It's catchy, you like it. Press [enter] to agree", 1, 1);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		//add to buffer while not [enter], compare against answer, update maze
		
		if(key.getKeyCode() == KeyEvent.VK_ENTER)
		{
			//compare to answer
			buffer = buffer.toUpperCase();
			maze.updateMaze(direction, true);
			maze.movePlayer(direction);
			return parent;
		}
		buffer += key.getKeyChar();
		return this;
	}
}
