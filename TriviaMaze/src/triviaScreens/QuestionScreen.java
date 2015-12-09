package triviaScreens;

import java.awt.event.KeyEvent;
import java.util.List;

import asciiPanel.AsciiPanel;
import maze.Maze;
import question.Question;

public class QuestionScreen implements Screen {

	private Screen parent;
	private Maze maze;
	private int direction;
	private String buffer = "";
	private Question question;
	private boolean debug = true;
	
	//SQL lite object or strings needed
	
	QuestionScreen(Screen par, Maze m, int dir, Question q)
	{
		parent = par;
		maze = m;
		direction = dir;
		question = q;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		// TODO Auto-generated method stub
		int lineLen = 58;
		String buff = question.getQuestion().substring(0, lineLen);
		int i = 0;
		
		String q = question.getQuestion();
		if(q.length() < lineLen)
			terminal.write(q);
		else
		{
			while(buff.length() != 0)
			{
				if(lineLen * (i + 1) > q.length())
					buff = q.substring(i * lineLen);
				else
					buff = q.substring(lineLen * i, lineLen * (i + 1));
				terminal.write(buff, 1, i + 1);
				if(buff.length() < lineLen)
				{
					buff = "";
				}
				i++;
			}
		}
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		//add to buffer while not [enter], compare against answer, update maze
		
		if(key.getKeyCode() == KeyEvent.VK_ENTER)
		{
			maze.updateMaze(direction, question.isAnswerCorrect(buffer));
			maze.movePlayer(direction);
			return parent;
		}
		buffer += key.getKeyChar();
		return this;
	}
}
