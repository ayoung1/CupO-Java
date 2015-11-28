<?php
	include creds.php;

	function submitTrueFalse(){
		
		
	}
	
	function submitOneWord(){
		
		
	}
	
	$questionType = $_POST['type'];
	
	if($questionType == 'truefalse'){
		submitTrueFalse();
	}else if($questionType == 'oneword'){
		submitOneWord();
	}
?>