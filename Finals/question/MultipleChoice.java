/*
 * Aaron Young
 */
package question;

import java.util.Hashtable;

public class MultipleChoice extends Question {

	private String[] options;
	
	public MultipleChoice(int _id, String _question, String _answer, String ... _options) {
		super(_id, QuestionType.MultipleChoice, _question, _answer);
		this.options = _options;
	}	
	
	@Override
	public Hashtable<String, String> getArgs() {
		Hashtable<String, String> args = new Hashtable<String, String>();
		
		args.put("id", ""+this.getId());
		args.put("question", this.getQuestion());
		args.put("answer", this.getAnswer());
		for(int i = 1; i <= this.options.length; i++)
			args.put("option"+i, options[i-1]);
		
		return args;
	}

}
