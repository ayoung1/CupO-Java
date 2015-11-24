var tableCount = 0;
var offset = 20;
var shown = false;
var maxMultiple = 4
var minMultiple = 1
var minlength = 10
var maxlength = 50

function login(){
	var username = $("#username").val();
	var pass = $('#password').val();
	var msg = "";
	
	if($("#username").val().length == 0){
		showError('Username cannot be blank');
	}
	else{
		$.post('php/adminlog.php', {username: username, pass: pass}, function(data){
			if(data === "Success"){
				$(location).attr('href', 'php/adminpage.php');
			}
		})
	}
	
}

function submitQuestion(ID){
	if(isValidQuestion()){
		
	}
	else{
		
	}
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

function isValidQuestion(){
	var question = $("#question").val().trim();
	var errormsg = "";
	
	if(!question){
		errormsg += ' Questions cannot be blank ';
		//return false;
	}
	if(question.length > maxlength || question.length < minlength){
		errormsg += ' Questions must be between '+minlength+' and '+maxlength+' characters ';
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
	$("#errormsg").addClass("hide");
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
	for(tableCount; tableCount >= 2; tableCount++){
		$("#multipletable").append('<input id="multipleoption' + tableCount + '" maxlength=50 type="text"/>');
	}
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

$(document).ready(function(){
	setUpPage();
	
	$('#changeform').click(function(){
		toggleLogin();
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
	
	$("#logsubmit").click(function(){
		login();
	});
	
	$("input:button[name=submit]").click(function(){
		submitQuestion();
	});
	
	$("input:radio[name=type]").click(function(){
		display($(this).val());
	});
	
	$("#removebutton").click(function(){
		event.preventDefault();
		removeElement();
	});
	
	$("#addbutton").click(function(){
		event.preventDefault();
		addElement();
	});
	
	$('#utesttoggle').click(function(){
		toggleUnitTests();
	});
});