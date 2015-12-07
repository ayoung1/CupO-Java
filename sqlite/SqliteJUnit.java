package sqlite;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import question.*;

public class SqliteJUnit {

	final int count = 5;
	final int pull = 1;
	String dbname = "test.db";
	String[] args = {"Is this a question?", "T"};
	Sqlite slite;
	
	@Before
	public void setUp() throws Exception {
		slite = new Sqlite();
		slite.connect(dbname);
	}

	@After
	public void tearDown() throws Exception {
		slite.clearDatabase();
	}

	@Test
	public void test() {
		int i = 1;
		int[] exclusion = {1,2,3,4,5,6,7,8,9,10};
		int[] blank = {};
		
		assertEquals("Not clearing db correctly : ", true, slite.clearDatabase());
		assertEquals("Doesn't handle empty clears correctly : ", false, slite.clearDatabase());
		
		assertEquals("Connects incorrectly : ", true, slite.connect(dbname));
		
		assertEquals("Add to database : ", true, slite.add(Sqlite.TRUE_FALSE, args));
		assertEquals("Archive count : ", 1, slite.countArchive());
		
		for(; i < count; i++)
			slite.add(Sqlite.TRUE_FALSE, args);
		
		assertEquals("Archive count after multiple inserts : ", count, slite.countArchive());
		assertEquals("Not returning Null on unreachable : ", null, slite.queryAll(count + 1));
		
		ArrayList<Question> list = slite.queryAll(0);
		
		assertEquals("Not pulling all : ", count, list.size());
		
		slite.setExclusions(exclusion);
		list = slite.queryAll(0);
		
		assertEquals("Not excluding correctly : ", 1, list.size());
		
		list = slite.queryRandom(0, pull);
		
		assertEquals("Not limiting selection : ", pull, list.size());
		
		Question question = list.get(0);
		Question test = new TrueFalse(0, args[0], args[1]);
		
		assertEquals("Question not creating args correctly : ", test.getArgs(), question.getArgs());
		assertEquals("Question not comparing answers correctly : ", true, question.isAnswerCorrect(question.getAnswer()));
	}

}