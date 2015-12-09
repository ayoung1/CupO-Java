//Bryce Fenske

//scrapes questions off the web page, makes a database through sqlite
//returns name of db or null if an error occurred

package driver;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import question.Question;
import question.TrueFalse;
import sqlite.Sqlite;

public class ArchiveScraper {

	private final String domain = "http://aaronyoungproductions.com/php/archive.php";
	private int TIMEOUT = 10*1000;
	private String QUESTION = "id=\"question\">";
	private String ANSWER = "id=\"answer\">";
	private String QID = "<tr id=\"";
	//TODO
	private String OPTION = "";
	private Sqlite sql;
	private String DATABASE = "test.db";
	
	public void main(String[] args) {
		System.out.println("running");
		sql = new Sqlite();
		scrape();
	}

	public String scrape() {
		Document doc;
		sql = new Sqlite();
		try {
			
			sql.connect(DATABASE);
			sql.clearDatabase();
			sql.connect(DATABASE);
			
			doc = Jsoup.connect(domain).get();
			
			doc = Jsoup.connect(domain).timeout(TIMEOUT).get();
			Elements qs;
			
			
			qs = doc.select("table[id*=true_false]");
			processTFSA(qs, true);
			
			
			qs = doc.select("table[id*=short_answer]");
			processTFSA(qs, false);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Dead. Check your internet connection and the site passed. "
					+ "Timeout is currently: " + TIMEOUT + " ms");
			return null;
		}
		return DATABASE;
	}
	
	
	private void processTFSA(Elements qs, boolean tf)
	{

		int i = 0;
		for(Element e: qs)
		{
			int qNum = 0;
			int index = 0;
			String buffer = e.toString();
			String temp;
			String q;
			String ans;
			String[] sqlArgs = new String[2];
			
			index = buffer.indexOf(QID);
			temp = buffer;
			while(index != -1)
			{
				qNum++;
				temp = temp.substring(index + 1);
				index = temp.indexOf(QID);
				
			    //id = temp.substring(temp.indexOf("=\"") + 2, temp.indexOf("\">"));
				q = temp.substring(temp.indexOf(QUESTION) + QUESTION.length(), temp.indexOf("</"));
				
				index = temp.indexOf(ANSWER);
				temp = temp.substring(index);
				
				
				ans = temp.substring(temp.indexOf(ANSWER) + ANSWER.length(), temp.indexOf("</"));
				
				/*
				ans = ans.replace("\"", "\\\"");
				ans = ans.replace("\'", "\\\'");
				q = q.replace("\"", "\\\"");
				q = q.replace("\'", "\\\'");
				*/ //single quotes thow error
				
				ans = ans.replace("\"", "");
				ans = ans.replace("\'", "");
				q = q.replace("\"", "");
				q = q.replace("\'", "");
				
				if(tf)
				{
					sql.add(sql.TRUE_FALSE, new String[]{q, ans});
				}else
				{
					sql.add(sql.SHORT_ANSWER, new String[]{q, ans});
				}
				index = temp.indexOf(QID);
				
			}
			System.out.println("Questions: " + qNum);
			
		}
	}
	
	
	private void processMC(Elements qs)
	{
		//not yet implemented
	}
}
