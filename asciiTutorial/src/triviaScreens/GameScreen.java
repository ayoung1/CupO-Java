//Bryce Fenske

//displayTiles, getScrollX/Y are found in trystan's tutorial

package triviaScreens;

import java.awt.Color;
import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import maze.Maze;
import maze.Room;

//check display for max maze size (difficulty hard) so no resize shenanigans

public class GameScreen implements Screen{

	private static final String DEFAULT_DIFFICULTY = "regular";
	private final String CHEAT = "tom op";
	private String cheatEntered = "";
	private Screen parent;
	private Maze maze;
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
		screenWidth = maze.getCharGrid().length;
		screenHeight = maze.getCharGrid()[0].length; //TODO
		centerX = maze.getCharGrid().length/2;
		centerY = centerX;	
	}
	
	public GameScreen(Screen par)
	{ 
		this(par, new Maze(DEFAULT_DIFFICULTY));
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		// TODO Auto-generated method stub
		displayTiles(terminal, 0, 0);//replace this with player x, y
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(maze.mazeIsSolvable()) //this logic can be consolidated
		{
			int dir = -1;
			switch(key.getKeyCode())
			{
				case KeyEvent.VK_W:
					dir = Room.WEST;
				break;
				case KeyEvent.VK_A:
					dir = Room.SOUTH;
					break;
				case KeyEvent.VK_S:
					dir = Room.EAST;
					break;
				case KeyEvent.VK_D:
					dir = Room.NORTH;
					break;		
			/*
				case KeyEvent.VK_W:
					dir = Room.NORTH;
				break;
				case KeyEvent.VK_A:
					dir = Room.WEST;
					break;
				case KeyEvent.VK_S:
					dir = Room.SOUTH;
					break;
				case KeyEvent.VK_D:
					dir = Room.EAST;
					break;
					*/
				default:
					dir = -1;
					System.out.println("not a move key");
			}
			if(dir != -1)
			{
				if(maze.isADoor(dir))
				{
					return new QuestionScreen(this, maze, dir);
				}
				maze.movePlayer(dir);
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
