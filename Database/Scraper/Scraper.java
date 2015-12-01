//TODO
//Intent revealing
//NEED ? AT THE END OF EACH Q
//ANSWERS < 50 char
//TOSS INBEDDED MEDIA
//Toss cases where str2 isn't found
//NORMALIZE Q and A STRINGS: /, &
//GET CATEGORY (optional)

//THEN:
//INSERT INTO DATABASE
//GENERATE OTHER OPTIONS

import java.io.IOException;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Scraper {
	
	private static LinkedList <String> questions;
	private static final String str = "response\">";
	private static final String str2 = "stuck', '";
	
	public static void main(String[] args)
	{
		crawl();
	}
	
	public static void crawl()
	{
		String crawlDomain = "http://www.j-archive.com/showgame.php?game_id=1";
		Document doc;
		LinkedList <String> questions = new LinkedList<String>();//map

		
		try {
			doc = Jsoup.connect(crawlDomain).get();
			//Elements elm = doc.select("#mp-itn b a");
			
			/*doc.select()
		    This method is generally more powerful to use than the DOM-type getElementBy* methods, because multiple filters can be combined, e.g.:
		        el.select("a[href]") - finds links (a tags with href attributes)
		        el.select("a[href*=example.com]") - finds links pointing to example.com (loosely)
			 */
			
			doc = Jsoup.connect(crawlDomain).get();
			Elements qs;
			qs = doc.select("td[class*=clue_text]");
			//print(qs);
			System.out.println(qs.size());
			qs = doc.select("td[class*=clue_unstuck]");
			//print(qs);
			qs = doc.select("div[onmouseover]");

			print(qs);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("dead");
			e.printStackTrace();
		}
		System.out.println("done");
	}
	
	public static void print(Elements qs)
	{
		for(Element e: qs)
		{
			//questions.add(e.text());
			//System.out.println(e.text() + "\n");
			//System.out.println(e.attributes().size() + "\n");
			//System.out.println(e.attr("onmouseover"));
			int i = e.attr("onmouseout").indexOf(str2);
			int j = e.attr("onmouseout").substring(i + str2.length()).indexOf("')");
			System.out.println(e.attr("onmouseout").subSequence(i+str2.length(), i + str2.length() + j));
			
			
			i = e.attr("onmouseover").indexOf(str);
			j = e.attr("onmouseover").substring(i + str.length()).indexOf("<");
			System.out.println(e.attr("onmouseover").subSequence(i+str.length(), i + str.length() + j));
			System.out.println();
			
		}
		System.out.println(qs.size());
	}
}
