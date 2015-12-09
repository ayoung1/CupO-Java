package getJeopardy;
//Bryce Fenske

//Driver for taking questions from jarchive and adding them to the database
//games 1-10 in db already

//todo: deal w/duplicate insertions in online db
//get user input rather than hard code

import java.sql.SQLException;
import java.util.HashMap;

public class ParseJeopardyToDB {
	private static int START_PARSE = 1;
	private static int NUM_GAMES = 10;
	private static JeopardyScraper jeoScrape;
	private static HashMap <String, String> qna;
	private static PutShortAnswer putSA = new PutShortAnswer();
	
	public static void main(String[] args)
	{
		jeoScrape = new JeopardyScraper();
		try {
			if(putSA.openConnection())
			{
				for(int i = START_PARSE; i < START_PARSE + NUM_GAMES; i++)
				{
					jeoScrape.setGameNumber(i);
					qna = jeoScrape.crawl();
					putSA.setSA(qna);
					putSA.putQuestions();
				}
				
				putSA.closeConnection();
				System.out.println("Scrape sucessful");
			}
		} catch (InstantiationException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
