var tableCount = 0;
var offset = 20;
var shown = false;
var maxMultiple = 5
var minMultiple = 2
var minlength = 10

function submitForm(ID){
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
	
	if(!question){
		showError('Questions cannot be blank');
		return false;
	}
	else if(question.length < minlength){
		showError("Questions must be longer than "+minlength+" characters")
		return false
	}
	else if(question.substr(question.length - 1) != "?"){
		showError("Questions must end with a '?'")
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

function setUpMultiples(){
	
	for(i=0; i < 3; i++){
		tableCount++;
		$("#multipletable").append('<input id="multipleoption' + tableCount + '" maxlength=50 type="text"/>');
	}
};

$(document).ready(function(){
	$("#question").val("Enter Your Question!");
	
	setUpMultiples();
	
	$('#question').on('input', function(){
		hideError();
	});
	
	$('#help').hover(function(){
		$('body').append("<div id='hoveringTooltip' style='position:fixed;'></div>");
		$('#hoveringTooltip').html("Check the type of answer you want");
		$('#hoveringTooltip').css({
			"top" : $(this).offset().top + offset,
			"left" : $(this).offset().left + offset,
			"background-color" : "lightblue",
			"padding" : "5px",
			"border-radius" : "10px"
		})},
		function(){
		$('#hoveringTooltip').remove();
		}
	);
	
	$("input:button[name=submit]").click(function(){
		submitForm();
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
		var test = $('#unittest');
	
		if(test.hasClass("hide")){
			test.removeClass('hide');
		}else{
			test.addClass('hide');
		}
	});
});