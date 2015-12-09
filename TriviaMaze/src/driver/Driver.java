//Bryce Fenske

//note: asciiPanel panel can be found here:  https://github.com/trystan/AsciiPanel
//tutorials from the creator: trystans.blogspot.com/2011/08/roguelike-tutorial-01-java-eclipse.html

package driver;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JFrame;
import asciiPanel.AsciiPanel;
import question.Question;
import sqlite.Sqlite;
import triviaScreens.Screen;
import triviaScreens.StartScreen;


public class Driver  extends JFrame implements KeyListener{

	//for serializing if needed
	private static final long serialVersionUID = 1060623638149583738L;
	private static final String DATABASE = "test.db";
	private static String gendb;
	private static AsciiPanel terminal;
	private static Screen screen;
	private static Sqlite sql;
	
	public Driver(){
		super();
		
		sql = new Sqlite();
		
		if(sql.connect(DATABASE))
		{
			System.out.println(DATABASE);
			System.out.println();
			ArchiveScraper arcscrape = new ArchiveScraper();
			gendb = arcscrape.scrape();
			if(gendb != null)
			{
				sql.connect(gendb);
			}
			else
				error();
		}
		init();
	}
	
	public static void error()
	{
		System.err.println("Could not generate database. Check your internet connection");
        System.exit(ABORT);	
	}
	
	private void init()
	{
		terminal = new AsciiPanel(60, 30); //displays as (i, 2j)
		
        this.add(terminal);
        this.setResizable(false);
        this.pack();
        screen = new StartScreen(this);
        this.addKeyListener(this);
        this.repaint();
	}
	
	public static void main(String[] args)
	{
		Driver app = new Driver();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		screen = screen.respondToUserInput(arg0);
		if(screen == null)
			System.exit(1);
        repaint();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
	
	
    public void repaint(){
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }
    
    public static void exit()
    {
    	System.exit(NORMAL);
    }
    
    
    public static List<Question> getQuestions(int doors)
    {
    	return sql.queryRandom(doors, doors + 1);
    }
    
    

}