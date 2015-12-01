<?php
	function verifyQuestion(){
		if(strlen($_POST['question']) < 10 || strlen($_POST['question']) > 50){
			echo "Failed";
			die();
		}else if(substr($_POST['question'], -1) != '?'){
			echo "Failed";
			die();
		}
	}
	
	function submitMultiple($creds){
		include $creds;
		
		if(!isset($_POST['answer'])){
			echo "Failed";
			die();
		}
		
		if($_POST['answer'] == 'true' || $_POST['answer'] == 'false'){
			$answer = "NA";
			
			$mysqli = new mysqli($server, $user, $pw, $db);
			
			if($_POST['answer'] == 'true')
				$answer = "T";
			else
				$answer = "F";
			
			if(!($stmt = $mysqli->prepare("INSERT INTO MULTIPLE_CHOICE VALUES(NULL,?,?)")))
			{
				echo "Prepare Failed";
				$mysqli->close();
				die();
			}
			
			if(!$stmt->bind_param("ss", $answer, $_POST['question']))
			{
				echo "Bind failed";
				$mysqli->close();
				die();
			}
			
			if(!$stmt->execute())
			{
				echo "Fail";
				$mysqli->close();
				die();
			}
		}else{
			echo "Submition Failed";
			die();
		}
		echo "Success";
	}
	
	function submitTrueFalse($creds){
		include $creds;
		
		if(!isset($_POST['answer'])){
			echo "Failed";
			die();
		}
		
		if($_POST['answer'] == 'true' || $_POST['answer'] == 'false'){
			$answer = "NA";
			
			$mysqli = new mysqli($server, $user, $pw, $db);
			
			if($_POST['answer'] == 'true')
				$answer = "T";
			else
				$answer = "F";
			
			if(!($stmt = $mysqli->prepare("INSERT INTO TRUE_FALSE VALUES(NULL,?,?)")))
			{
				echo "Prepare Failed";
				$mysqli->close();
				die();
			}
			
			if(!$stmt->bind_param("ss", $answer, $_POST['question']))
			{
				echo "Bind failed";
				$mysqli->close();
				die();
			}
			
			if(!$stmt->execute())
			{
				echo "Fail";
				$mysqli->close();
				die();
			}
		}else{
			echo "Submition Failed";
			die();
		}
		echo "Success";
	}
	
	function submitOneWord($creds){
		include $creds;
		
		if(!isset($_POST['answer'])){
			echo "Failed";
			die();
		}
		
		if(strlen($_POST['answer']) >= 10 && strlen($_POST['answer']) <= 50){
			
			$mysqli = new mysqli($server, $user, $pw, $db);
			
			if(!($stmt = $mysqli->prepare("INSERT INTO SHORT_ANSWER VALUES(NULL,?,?)")))
			{
				echo "Prepare Failed";
				$mysqli->close();
				die();
			}
			
			if(!$stmt->bind_param("ss", $_POST['answer'], $_POST['question']))
			{
				echo "Bind failed";
				$mysqli->close();
				die();
			}
			
			if(!$stmt->execute())
			{
				echo "Fail";
				$mysqli->close();
				die();
			}
		}else{
			echo "Submition Failed";
			die();
		}
		echo "Success";
	}
	
	$questionType = $_POST['type'];
	
	verifyQuestion();
	
	if($questionType == 'truefalse'){
		submitTrueFalse("creds.php");
	}else if($questionType == 'oneword'){
		submitOneWord("creds.php");
	}else if($questionType == 'multiple'){
		submitMultiple("creds.php");
	}
?>