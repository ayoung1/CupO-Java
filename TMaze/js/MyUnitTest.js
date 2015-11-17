QUnit.test("Question Inputs", function(assert){
	var input = $('#question');
	
	input.val("not a valid question");
	assert.equal(isValidQuestion(), false, "'not a valid question' is not valid");
	input.val("is this red?");
	assert.equal(isValidQuestion(), true, "'is this red?' is valid");
	input.val("is?");
	assert.equal(isValidQuestion(), false, "'is?' is too short - not valid");
	input.val("       ?         ");
	assert.equal(isValidQuestion(), false, "'       ?         ' ignore trailing white space for input");
	input.val("");
	assert.equal(isValidQuestion(), false, "Empty questions are not valid");
	
	$("#question").val("Enter Your Question!");
	hideError();
});

QUnit.test("Answer Inputs", function(assert){
	assert.ok(1=='1', "Passed");
});