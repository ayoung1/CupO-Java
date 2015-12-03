<?php
	include "../creds.php";
	
	$mysqli = new mysqli($server, $user, $pw, $db);
	
	if(!isset($_POST['id'])){
		echo "Something went wrong";
		die();
	}else if(!isset($_POST['table'])){
		echo "Something went wrong";
		die();
	}
	
	if($mysqli->connect_error){
		echo("Connect failed: %s\n". mysqli_connect_error());
        die();
	}
	if($_POST['table'] == 'TRUE_FALSE'){
		if(!$stmt = $mysqli->prepare("DELETE FROM TRUE_FALSE WHERE QUESTION_NUM = ?")){
			echo "Prepare Failed";
			$mysqli->close();
			die();
		}
	}else if($_POST['table'] == 'SHORT_ANSWER'){
		if(!$stmt = $mysqli->prepare("DELETE FROM SHORT_ANSWER WHERE QUESTION_NUM = ?")){
			echo "Prepare Failed";
			$mysqli->close();
			die();
		}
	}else if($_POST['table'] == 'MULTIPLE_CHOICE'){
		if(!$stmt = $mysqli->prepare("DELETE FROM MULTIPLE_CHOICE WHERE QUESTION_NUM = ?")){
			echo "Prepare Failed";
			$mysqli->close();
			die();
		}
	}else{
		echo "Bad table";
		$mysqli->close();
		die();
	}
	if(!$stmt->bind_param("i", intval($_POST['id']))){
		echo "Bind failed";
		$mysqli->close();
		die();
	}
	if(!$stmt->execute())
	{
		echo "Execute Failed";
		$mysqli->close();
		die();
	}
	echo "Success";
	$mysqli->close();
?>