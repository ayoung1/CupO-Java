package question;

import java.util.Hashtable;

public class TrueFalse extends Question {

	public TrueFalse(int _id, String _question,	String _answer) {
		super(_id, QuestionType.TrueFalse, _question, _answer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Hashtable<String, String> getArgs() {
		Hashtable<String, String> args = new Hashtable<String, String>();
		
		args.put("id", ""+this.getId());
		args.put("question", this.getQuestion());
		args.put("answer", this.getAnswer());
		
		return args;
	}

}
