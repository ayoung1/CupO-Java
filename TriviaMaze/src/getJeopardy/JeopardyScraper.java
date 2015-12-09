package getJeopardy;


//currently 1-5121 games in jarchive 12/6/15
//this program will parse all questions not final jeopardy and without imbedded media from valid j-archive
//game pages

//TODO: category. some questions are nonsense without it
//generalize class as much as possible, allow for input and chaining
//ie: jeopardyscraper [gameno] | generateQuestions | putDatabase
//double check logical flow and step time complexity
//code smells

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class JeopardyScraper {

	
	
	//java script located prior to relevant info
	private final String A_STR = "response\">";
	private final String Q_STR = "stuck', '";
	private final String Q_ATTR = "onmouseout";
	private final String A_ATTR = "onmouseover";
	private final String CSS = "div[onmouseover]";
	private final int TIMEOUT = 10*1000; //ms, 10 sec
	private final String DOMAIN = "http://www.j-archive.com/showgame.php?game_id=";
	private final String MEDIA = "<a href";
	
	private HashMap <String, String> qna = new HashMap<>();
	private int gameNumber = 5121;
	private String crawlDomain = DOMAIN + Integer.toString(gameNumber);
	
	
	public JeopardyScraper(){
		this(1);
	};
	
	public JeopardyScraper (int gameNum)
	{
		gameNumber = gameNum;
		crawlDomain = DOMAIN + Integer.toString(gameNumber);
	}
	
	/*
	public void main(String[] args)
	{
		crawl();
	}
	*/
	public int getGameNumber(){return gameNumber;}
	
	public void setGameNumber(int g){gameNumber = g;}
	
	public HashMap<String, String> crawl()
	{		
		Document doc;
		try {
			doc = Jsoup.connect(crawlDomain).get();
			doc = Jsoup.connect(crawlDomain).timeout(TIMEOUT).get();
			Elements qs;
	
			qs = doc.select(CSS);

			process(qs);
			System.out.println("done\n");
			return qna;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Dead. Check your internet connection and the site passed. "
					+ "Timeout is currently: " + TIMEOUT + " ms");
			return null;
		}

	}
	
	public void process(Elements qs)
	{
		int i, usable = 0;
		String question, answer;
		
		//System.out.println("jarchive game no. " + gameNumber + " usable questions:");
		for(Element e: qs)
		{
			i = e.attr(Q_ATTR).indexOf(Q_STR);
			if( i != -1) //if q in general format
			{
				int j = e.attr(Q_ATTR).substring(i + Q_STR.length()).indexOf("')");
				question = e.attr(Q_ATTR).subSequence(i+Q_STR.length(), i + Q_STR.length() + j).toString();
				
				i = e.attr(A_ATTR).indexOf(A_STR);
				if( (i != -1) && !question.contains(MEDIA)) //a in gen format and q no media
				{
					j = e.attr(A_ATTR).substring(i + A_STR.length()).indexOf("<");
					answer= e.attr(A_ATTR).subSequence(i+A_STR.length(), i + A_STR.length() + j).toString();
										
					if(answer.length() > 0 && answer.replace("the", "").trim().length() > 0)//answer not empty (edge case)
					{
						question = question.trim();
						if(question.lastIndexOf('?') != question.length() - 1)
							question += "?";
						answer = answer.trim();
						
						//System.out.println(question);
						//System.out.println(answer);
						
						//System.out.println();
						qna.put(question, answer);
						
						usable++;
					}
				}
			}
		}
		System.out.println("Total questions: " + qs.size());
		System.out.println("Usable questions: " + usable);
	}
}
