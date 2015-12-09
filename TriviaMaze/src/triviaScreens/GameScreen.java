//Bryce Fenske

//displayTiles, getScrollX/Y are found in trystan's tutorial

package triviaScreens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Driver;

import asciiPanel.AsciiPanel;
import maze.Maze;
import maze.Room;
import question.Question;

//check display for max maze size (difficulty hard) so no resize shenanigans

public class GameScreen implements Screen{

	private static final String DEFAULT_DIFFICULTY = "regular";
	private final String CHEAT = "tom op";
	private String cheatEntered = "";
	private Screen parent;
	private Maze maze;
	private List<Question> questions;
	private List<Question> asked;
	private boolean hasQuestions = false;
	private int screenWidth;
	private int screenHeight;
	private int centerX;
	private int centerY;
	
	public GameScreen(Screen par, Maze m)
	{
		parent = par;
		maze = m;
		init();
	}
	
	private void init()
	{
		//doesn't support generic grid dimensions yet
		screenWidth = maze.getCharGrid()[0].length;
		screenHeight = maze.getCharGrid().length;
		centerX = maze.getCharGrid().length/2;
		centerY = centerX;
		questions = driver.Driver.getQuestions(maze.getNumOfDoors());
		if(questions != null)
		{
			hasQuestions = true;
		}
		asked = new ArrayList<Question>();
	}
	
	public GameScreen(Screen par)
	{ 
		this(par, new Maze(DEFAULT_DIFFICULTY));
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		if(!hasQuestions)
		{
			terminal.write("There aren't enough questions", 1,1);
			terminal.write("in the database to play!", 1, 2);
		}
		else
		displayTiles(terminal, 0, 0);//replace this with player x, y
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(hasQuestions)
		{
			if(maze.mazeIsSolvable())
			{
				int dir = -1;
				switch(key.getKeyCode())
				{
					
					case KeyEvent.VK_W:
						dir = Room.WEST;
					break;
					case KeyEvent.VK_A:
						dir = Room.NORTH;
						break;
					case KeyEvent.VK_S:
						dir = Room.EAST;
						break;
					case KeyEvent.VK_D:
						dir = Room.SOUTH;
						break;		
					default:
						dir = -1;
				}
				if(dir != -1)
				{
					if(maze.isADoor(dir))
					{
						Question q = questions.remove(0);
						asked.add(q);
						return new QuestionScreen(this, maze, dir, q);
					}
					maze.movePlayer(dir);
					if(maze.winningTile())
						return new WinScreen();
				}else
				{
					cheatEntered += key.getKeyChar();
					if(cheatEntered.length() == CHEAT.length())
					{
						if(cheatEntered.compareTo(CHEAT) == 0)
						{
							maze.cheatPath();
						}
						cheatEntered = cheatEntered.substring(1);
					}
				}
				return this;
			}
			return new LoseScreen(this);	
		}
		driver.Driver.exit();
		return this;
	}
	
	
	public int getScrollX() { //assumes square grid. need dimension getters from maze
	    return Math.max(0, Math.min(centerX - screenWidth / 2, maze.getCharGrid().length - screenWidth));
	}


	public int getScrollY() {
	    return Math.max(0, Math.min(centerY - screenHeight / 2, maze.getCharGrid().length - screenHeight));
	}

	private void displayTiles(AsciiPanel terminal, int left, int top) {
	    for (int x = 0; x < screenWidth; x++){
	        for (int y = 0; y < screenHeight; y++){
	            int wx = x + left;
	            int wy = y + top;
	            Color color = Color.WHITE;
	            if(maze.getCharGrid()[wx][wy] == 'P')
	            	color = Color.GREEN;
	            else if(maze.getCharGrid()[wx][wy] == 'F')
	            	color = Color.RED;
	            else if(maze.getCharGrid()[wx][wy] == '#')
	            	color = Color.YELLOW;
	            terminal.write(maze.getCharGrid()[wx][wy], x, y, color);
	        }
	    }
	}
}
