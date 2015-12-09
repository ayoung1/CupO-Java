/*
 * Aaron Young
 */
package question;

import java.util.Hashtable;

public class ShortAnswer extends Question {

	public ShortAnswer(int _id, String _question, String _answer) {
		super(_id, QuestionType.ShortAnswer, _question, _answer);
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
