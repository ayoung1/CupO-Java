<?php

	function quit($message){

		echo $message;

		die();

	}



	function verifyQuestion($creds){

		include $creds;

		

		if(!isset($_POST['question'])){

			quit('Question not set');

		}

		if(strlen($_POST['question']) < 10 || strlen($_POST['question']) > 255){

			quit('Question length error');

		}else if(substr($_POST['question'], -1) != '?'){

			quit("Question missing '?'" );

		}

	}

	

	function submitMultiple($creds){

		include $creds;

		

		if(!isset($_POST['answer'])){

			echo "Failed";

			die();

		}

		

		if(isset($_POST['options'])){

			$options = $_POST['options'];

			

			$mysqli = new mysqli($server, $user, $pw, $db);

			

			if(!($stmt = $mysqli->prepare("INSERT INTO multiple_choice VALUES(NULL,?,?,?,?,?,?,?)")))

			{

				echo "Prepare Failed";

				$mysqli->close();

				die();

			}

			

			if(!$stmt->bind_param("issssss", intval($_POST['numberoptions']), $_POST['question'], $_POST['answer'], $options[0], $options[1], $options[2], $options[3] ))

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

		$mysqli->close();

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

			

			if(!($stmt = $mysqli->prepare("INSERT INTO true_false VALUES(NULL,?,?)")))

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

		$mysqli->close();

	}

	

	function submitOneWord($creds){

		include $creds;

		

		if(!isset($_POST['answer'])){

			echo "Failed";

			die();

		}

		

		if(strlen($_POST['answer']) >= 1 && strlen($_POST['answer']) <= 50){

			

			$mysqli = new mysqli($server, $user, $pw, $db);

			

			if(!($stmt = $mysqli->prepare("INSERT INTO short_answer VALUES(NULL,?,?)")))

			{

				echo "Prepare Failed";

				$mysqli->close();

				die();

			}

			

			if(!$stmt->bind_param("ss", $_POST['question'], $_POST['answer']))

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

		$mysqli->close();

	}

	

	$questionType = $_POST['type'];

	

	verifyQuestion('creds.php');

	

	if($questionType == 'truefalse'){

		submitTrueFalse("creds.php");

	}else if($questionType == 'oneword'){

		submitOneWord("creds.php");

	}else if($questionType == 'multiple'){

		submitMultiple("creds.php");

	}

?>