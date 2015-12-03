var tableCount = 1;

var offset = 20;

var shown = false;

var cleared = false;

var maxMultiple = 4;

var minMultiple = 1;

var minlength = 2;

var maxQuestionLength = 255;

var maxlength = 50;



function login(){

	var username = $("#username").val();

	var pass = $('#password').val();

	var msg = "";

	

	if(username.length == 0){

		showError('Username cannot be blank');

		return false;

	}

	else if(pass.length == 0){

		showError('Password cannot be blank');

		return false;

	}

	else{

		$.post('php/adminlog.php', {username: username, pass: pass}, function(data){

			if(data === "Success"){

				$(location).attr('href', 'php/adminpage.php');

			}

			else{

				showError(data);

			}

		});

	}

	

}



function submitQuestion(ID){

	if(isValidQuestion()){

		

		if(ID === 'oneword' && isValidOneWord()){

			

			$.post('php/addQuestion.php', {type: 'oneword', question: $('#question').val(), answer: $('#wordanswer').val()}, function(data){

				if(data === "Success"){

					alert("Question submitted successfully");

					location.reload();

				}

				else{

					showError(data);

				}

			});

			

			return true;

		}

		else if(ID === 'multiplechoice' && isValidMultiple()){

			var choices = [];

			

			for(var i = 1; i <= tableCount; i++){

				choices[i-1] = $("#multipleoption" + i).val();

			}

			

			for(var i = tableCount+1; i <= maxMultiple; i++){

				choices[i-1] = null;

			}

			

			$.post('php/addQuestion.php', {type: 'multiple', question: $('#question').val(), answer: $('#multianswer').val(), numberoptions: tableCount, options: choices}, function(data){

				if(data === "Success"){

					alert("Question submitted successfully");

					location.reload();

				}

				else{

					showError(data);

				}

			});

			

			return true;

		}

		else if(ID === 'truefalse' && isValidTrueFalse()){

			

			$.post('php/addQuestion.php', {type: 'truefalse', question: $('#question').val(), answer: $("input:radio[name=truefalse]:checked").data('id')}, function(data){

				if(data === "Success"){

					alert("Question submitted successfully");

					location.reload();

				}

				else{

					showError(data);

				}

			});

			

			return true;

		}

	}

	return false;

};



function showError(message){

	if(!shown){

		shown = true;

		$('#errormsg').removeClass('hide');

		$('#errormsg').append('<p id="tempmessage" style="color:red">'+message+'</p>');

	}

};



function hideError(){

	if(shown){

		shown = false;

		$('#errormsg').addClass('hide');

		$('#tempmessage').remove();

	}

};



function isValidMultiple(){

	var answer = $('#multianswer').val();

	

	if(answer.length === 0){

		showError(' Answer cannot be blank ');

		return false;

	}

	else if(answer.length < minlength || answer.length > maxlength){

		showError(' Answer must be between '+minlength+' and '+maxlength+' characters ');

		return false;

	}



	for(var i = 1; i <= tableCount; i++)

	{

		var options = $('#multipleoption'+i).val();

		

		if(options.length === 0){

			showError(' Option ' + i + ' cannot be blank ');

			return false;

		}

		else if(options === answer){

			showError(' Option ' + i + ' cannot match the answer');

			return false;

		}

		else if(options.length < minlength || options.length > maxlength){

			showError(' Option ' + i + ' must be between '+minlength+' and '+maxlength+' characters ');

			return false;

		}

	}

	

	return true;

}



function isValidTrueFalse(){

	var answer = $("input:radio[name=truefalse]:checked").data('id');

	

	if(answer.toString() === "true" || answer.toString() === "false"){

		return true;

	}

	

	showError(" Please Select an Option ");

	

	return false;

}



function isValidOneWord(){

	var answer = $("#wordanswer").val();

	

	if(answer.length < minlength || answer.length > maxlength){

		showError(' Answer must be between '+minlength+' and '+maxlength+' characters ');

		return false;

	}

	

	return true;

}



function isValidQuestion(){

	var question = $("#question").val().trim();

	var errormsg = "";

	

	if(!question){

		errormsg += ' Questions cannot be blank ';

		//return false;

	}

	if(question.length > maxQuestionLength || question.length < minlength){

		errormsg += ' Questions must be between '+minlength+' and '+maxQuestionLength+' characters ';

		//return false;

	}

	if(question.substr(question.length - 1) != "?"){

		errormsg += " Questions must end with a '?'";

		//return false;

	}

	if(errormsg){

		showError(errormsg);

		return false;

	}

	hideError();

	return true;

};



function display(section){

	$("#truefalse").addClass("hide");

	$("#oneword").addClass("hide");

	$("#multiplechoice").addClass("hide");

	

	$("#" + section).removeClass("hide");

	hideError();

};



function addElement(){

	if(tableCount < maxMultiple){

		tableCount++;

		$("#multipletable").append('<span><input id="multipleoption'+tableCount+'" maxlength=50 type="text"/></span>');

	}

};



function removeElement(){

	if(tableCount > minMultiple){

		$("#multipleoption"+tableCount).remove();

		tableCount--;

	}

};



function setUpPage(){

	$("#question").val("Enter Your Question!");

	$("#adminfooter").append('<p id="questionfooter" class="footer-link center">Admin? Click <a id="changeform" href="#Admin">here</a> to login.</p>');

	for(tableCount; tableCount <= minMultiple; tableCount++){

		$("#multipletable").append('<input id="multipleoption' + tableCount + '" maxlength=50 type="text"/>');

	}

	tableCount--;

};



function helpHover(doc, ID, message){

	$('body').append("<div id='"+ID+"' style='position:fixed;'></div>");

	$('#'+ ID).html(message);

	$('#' + ID).css({

		"top" : $(doc).offset().top + offset,

		"left" : $(doc).offset().left + offset,

		"background-color" : "lightblue",

		"padding" : "5px",

		"border-radius" : "10px"

	})

}



function removeHover(ID){

	$('#'+ID).remove();

}



function toggleLogin(){

	if($('#loginpage').hasClass('hide')){

		$("#questionfooter").remove();

		$("#adminfooter").append('<p id="questionfooter" class="footer-link center">Click <a id="changeform" href="#Question">here</a> to submit a question.</p>');

		$("#questionpage").addClass("hide");

		$("#loginpage").removeClass("hide");

	}else{

		$("#questionfooter").remove();

		$("#adminfooter").append('<p id="questionfooter" class="footer-link center">Admin? Click <a id="changeform" href="#Admin">here</a> to login.</p>');

		$("#questionpage").removeClass("hide");

		$("#loginpage").addClass("hide");

	}

	$('#changeform').click(function(){

		toggleLogin();

	});

	hideError();

}



function toggleUnitTests(){

	var test = $('#unittest');

	

	if(test.hasClass("hide")){

		test.removeClass('hide');

	}else{

		test.addClass('hide');

	}

}



function deleteElement(dataId){	
	

	if($("input:radio[name=type]:checked").val() == 'truefalse'){
		
		$.post("tables/delete.php", {id : dataId, type : 'tf'},

			function(data){

				if(data === "Success"){

					$("#truefalse").load(location.href+" #truefalse>*",function(){

						$("#truefalse input:button[name=delete]").on("click", function(){

							deleteElement($(this).data('id'));

						});

					});

				}

			});

	}else if($("input:radio[name=type]:checked").val() == 'oneword'){

		$.post("tables/delete.php", {id : dataId, type : 'sa'},

			function(data){

				if(data === "Success"){

					$("#oneword").load(location.href+" #oneword>*",function(){

						$("#oneword input:button[name=delete]").on("click", function(){

							deleteElement($(this).data('id'));

						});

					});

				}

			});

	}else if($("input:radio[name=type]:checked").val() == 'multiplechoice'){

		$.post("tables/delete.php", {id : dataId, type : 'mc'},

			function(data){

				if(data === "Success"){

					$("#multiplechoice").load(location.href+" #multiplechoice>*",function(){

						$("#multiplechoice input:button[name=delete]").on("click", function(){

							deleteElement($(this).data('id'));

						});

					});

				}

			});

	}

}



$(document).ready(function(){

	setUpPage();

	

	$('#changeform').on("click", function(){

		toggleLogin();

	});

	

	$('#question').focus(function(){

		if(!cleared){

			$('#question').val('');

			cleared = !cleared;

		}

	});

	

	$('#question').on('input', function(){

		hideError();

	});

	

	$('#help').hover(function(){

			helpHover(this, "helptooltip", "Check the type of answer you want");

		},

		function(){

			removeHover("helptooltip");

		}

	);

	

	$('#multihelp').hover(function(){

			helpHover(this, "helptooltip", "The answer will be included as an option");

		},

		function(){

			removeHover("helptooltip");

		}

	);

	

	$("#logsubmit").on("click", function(){

		login();

	});

	

	$("input:button[name=delete]").on("click", function(){

		deleteElement($(this).data('id'));

	});

	

	$("input:button[name=submit]").on("click", function(){

		submitQuestion($(this).data('id'));

	});

	

	$("input:radio[name=type]").on("click", function(){

		display($(this).val());

	});

	

	$("#removebutton").on("click", function(){

		event.preventDefault();

		removeElement();

	});

	

	$("#addbutton").on("click", function(){

		event.preventDefault();

		addElement();

	});

	

	$('#utesttoggle').on("click", function(){

		toggleUnitTests();

	});

});