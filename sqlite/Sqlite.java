package sqlite;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

import question.*;

public class Sqlite {
	public static final int TRUE_FALSE = 0;
	public static final int SHORT_ANSWER = 1;
	public static final int MULTIPLE_CHOICE = 2;
	
	private final int TF_LENGTH = 3;
	private final int SA_LENGTH = 3;
	private final int MC_MIN = 4;
	private final int MC_MAX = 7;
	private final String truefalse = "true_false";
	private final String shortanswer = "short_answer";
	private final String multiplechoice = "multiple_choice";
	
	private int[][] exclusions;
	private ArrayList<Question> pulledQuestions;
	private Connection connect;
	
	public Sqlite(){
		this.pulledQuestions = new ArrayList<Question>();
	}
	
	public boolean connect(String databaseName){
		File db = new File(databaseName);
		boolean existed = db.exists();
		
		try {
	      Class.forName("org.sqlite.JDBC");
	      connect = DriverManager.getConnection("jdbc:sqlite:"+databaseName);
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Opened database successfully");
	    this.exclusions = new int[Sqlite.MULTIPLE_CHOICE+1][];
	    for(int i = 0; i < Sqlite.MULTIPLE_CHOICE+1; i++)
	    	this.exclusions[i] = new int[0];
	    
	    if(!existed){
	    	createTables();
	    }
	    
		return !existed;
	}
	
	public void disconnect(){
		try {
			connect.close();
			System.out.println("Closed database successfully");
		} catch (SQLException e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
	}
	
	private void createTables(){
		createTrueFalse();
		createShortAnswer();
		createMultipleChoice();
	}
	
	private void createTrueFalse(){
		Statement stmt = null;
	    try {
	      stmt = connect.createStatement();
	      String sql = "CREATE TABLE `"+ truefalse +"` ("+
						  "`question_num` int(10) NOT NULL,"+
						  "`answer` varchar(1) NOT NULL,"+
						  "`question` varchar(256) NOT NULL"+
						");"; 
	      stmt.executeUpdate(sql);
	      
	      stmt.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Table created successfully");
	}
	
	private void createShortAnswer(){
		Statement stmt = null;
	    try {
	      stmt = connect.createStatement();
	      String sql = "CREATE TABLE `"+ shortanswer +"` ("+
						  "`question_num` int(10) NOT NULL,"+
						  "`answer_components` varchar(50) NOT NULL,"+
						  "`question` varchar(256) NOT NULL"+
						");"; 
	      stmt.executeUpdate(sql);
	      
	      stmt.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Table created successfully");
	}
	
	private void createMultipleChoice(){
		Statement stmt = null;
	    try {
	      stmt = connect.createStatement();
	      String sql = "CREATE TABLE `"+ multiplechoice +"` ("+
						  "`question_num` int(10) NOT NULL,"+
						  "`num_options` int(1) DEFAULT '2',"+
						  "`question` varchar(256) NOT NULL,"+
						  "`answer` varchar(50) NOT NULL,"+
						  "`option_1` varchar(50) NOT NULL,"+
						  "`option_2` varchar(50) DEFAULT NULL,"+
						  "`option_3` varchar(50) DEFAULT NULL,"+
						  "`option_4` varchar(50) DEFAULT NULL"+
						  ");";
	      stmt.executeUpdate(sql);
	      
	      stmt.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Table created successfully");
	}
	
	public void setExclusions(int[] trueFalse, int[] shortAnswer, int[] multipleChoice){
		this.exclusions[Sqlite.TRUE_FALSE] = trueFalse;
		this.exclusions[Sqlite.SHORT_ANSWER] = shortAnswer;
		this.exclusions[Sqlite.MULTIPLE_CHOICE] = multipleChoice;
	}
	
	
	public boolean add(int tag, String[] args){
		
		if(tag == Sqlite.TRUE_FALSE){
			if(args.length == TF_LENGTH){
				insertTrueFalse(args);
				return true;
			}else{
				System.err.println("Args length doesn't match tag value");
				System.exit(0);
			}
		}else if(tag == Sqlite.SHORT_ANSWER){
			if(args.length == SA_LENGTH){
				insertShortAnswer(args);
				return true;
			}else{
				System.err.println("Args length doesn't match tag value");
				System.exit(0);
			}
		}else if(tag == Sqlite.MULTIPLE_CHOICE){
			if(args.length >= MC_MIN && args.length <= MC_MAX){
				insertMultipleChoice(args);
				return true;
			}else{
				System.err.println("Args length doesn't match tag value");
				System.exit(0);
			}
		}else{
			System.err.println("Not a valid tag");
			System.exit(0);
		}
		
		return false;
	}
	
	private void insertTrueFalse(String[] args){
		String sql = "INSERT INTO "+ truefalse +"(question_num, question, answer)"+
					"VALUES(" + args[0] + ",'" + args[1] + "', '" + args[2] + "')";
		Statement stmt = null;
		
		try {
			stmt = connect.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
	}
	
	private void insertShortAnswer(String[] args){
		String sql = "INSERT INTO "+ shortanswer +"(question_num, question, answer_components)"+
				"VALUES(" + args[0] + ",'" + args[1] + "', '" + args[2] + "')";
		Statement stmt = null;
		
		try {
			stmt = connect.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
	}
	
	private void insertMultipleChoice(String[] args){
		int options = args.length - 3;
		String sql = "INSERT INTO "+ multiplechoice +"(question_num, num_options, question, answer, option_1, option_2, option_3, option_4)"+
				"VALUES(" + args[0] + options + ",'" + args[1] + "', '" + args[2] + ",'" + args[3] + "', '" + args[4] + "', '" + args[5] + "', '" + args[6] + "')";
		Statement stmt = null;
		
		try {
			stmt = connect.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
	}
	
	public int countArchive(){
		int count = 0;
		int index = 0;
		String[] tables = {truefalse, shortanswer, multiplechoice};
		String sql;
		Statement stmt;
		ResultSet set;
		
		for(String table : tables){
			sql = "SELECT COUNT(question_num) FROM " + table;
			
			sql = addException(index, sql);
			
			try {
				stmt = connect.createStatement();
				stmt.execute(sql);
				set = stmt.getResultSet();
				set.next();
				count += set.getInt(1);
				stmt.close();
			} catch (SQLException e) {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			    System.exit(0);
			}
			index++;
		}
		
		return count;
	}
	
	private String addException(int table, String sql){
		if(this.exclusions[table].length > 0){
			sql += " WHERE ";
			for(int i = 0; i < this.exclusions[table].length; i++){
				sql += "question_num <> " + this.exclusions[table][i];
				if(i != this.exclusions[table].length-1){
					sql += " AND ";
				}
			}
		}
		
		return sql;
	}
	
	private void pullTrueFalse(){
		String sql;
		Statement stmt;
		ResultSet set;
		Question question;
		int index = Sqlite.TRUE_FALSE;
		
		sql = "SELECT * FROM " + truefalse;
		sql = addException(index, sql);
		try{
			stmt = connect.createStatement();
			stmt.execute(sql);
			set = stmt.getResultSet();
			while(set.next()){
				question = new TrueFalse(set.getInt(1), set.getString(3), set.getString(2));
				this.pulledQuestions.add(question);
			}
		} catch (SQLException e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
	}
	
	private void pullShortAnswer(){
		String sql;
		Statement stmt;
		ResultSet set;
		Question question;
		int index = Sqlite.SHORT_ANSWER;
		
		sql = "SELECT * FROM " + shortanswer;
		sql = addException(index, sql);
		try{
			stmt = connect.createStatement();
			stmt.execute(sql);
			set = stmt.getResultSet();
			while(set.next()){
				question = new ShortAnswer(set.getInt(1), set.getString(3), set.getString(2));
				this.pulledQuestions.add(question);
			}
		} catch (SQLException e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
	}
	
	private void pullMultipleChoice(){
		String sql;
		Statement stmt;
		ResultSet set;
		Question question;
		int index = Sqlite.MULTIPLE_CHOICE;
		
		sql = "SELECT * FROM " + multiplechoice;
		sql = addException(index, sql);
		try{
			stmt = connect.createStatement();
			stmt.execute(sql);
			set = stmt.getResultSet();
			while(set.next()){
				question = new MultipleChoice(set.getInt(1), set.getString(3), set.getString(4), set.getString(5), set.getString(6), set.getString(7), set.getString(8));
				this.pulledQuestions.add(question);
			}
		} catch (SQLException e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
	}
	
	public ArrayList<Question> queryAll(int minRequired){
		if(countArchive() < minRequired)
			return null;
		this.pulledQuestions.clear();
		
		pullTrueFalse();
		pullShortAnswer();
		pullMultipleChoice();
		
		return this.pulledQuestions;
	}
	
	
	
	public ArrayList<Question> queryRandom(int minRequired, int numberQueried){
		if(this.queryAll(minRequired) == null)
			return null;
		Random rand = new Random();
		int pull;
		ArrayList<Question> temp = new ArrayList<Question>();
		
		for(int i = 0; i < numberQueried; i++){
			pull = rand.nextInt(this.pulledQuestions.size());
			temp.add(this.pulledQuestions.remove(pull));
		}
		
		return temp;
	}
}
