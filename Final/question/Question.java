/*
 * Aaron Young
 */
package question;

import java.util.Hashtable;

public abstract class Question {
	public static enum QuestionType{
		TrueFalse,
		ShortAnswer,
		MultipleChoice
	}
	
	private int id;
	private QuestionType questionType;
	private String question;
	private String answer;
	
	public Question(int _id, QuestionType _questionType, String _question, String _answer){
		this.id = _id;
		this.questionType = _questionType;
		this.question = _question;
		this.answer = _answer;
	}
	
	public int getId(){
		return this.id;
	}
	
	public QuestionType getQuestionType(){
		return this.questionType;
	}
	
	public String getQuestion(){
		return this.question;
	}
	
	public String getAnswer(){
		return this.answer;
	}
	
	public boolean isAnswerCorrect(String _answer){
		String answer = this.answer.toUpperCase();
		
		return answer.compareTo(_answer.toUpperCase()) == 0;
	}
	
	public abstract Hashtable<String, String> getArgs();
}
