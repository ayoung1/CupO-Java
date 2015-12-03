<?php
	session_start();
	include "../creds.php";

	if(!isset($_SESSION['user'])){
		echo "Not Logged In";
		die();
	}

	$mysqli = new mysqli($server, $user, $pw, $db);

	

	if(!isset($_POST['id'])){

		echo "Something went wrong";

		die();

	}else if(!isset($_POST['type'])){

		echo "Something went wrong";

		die();

	}

	

	if($mysqli->connect_error){

		echo("Connect failed: %s\n". mysqli_connect_error());

        die();

	}

	if($_POST['type'] == 'tf'){

		if(!$stmt = $mysqli->prepare("DELETE FROM true_false WHERE question_num = ?")){

			echo "Prepare Failed";

			$mysqli->close();

			die();

		}

	}else if($_POST['type'] == 'sa'){

		if(!$stmt = $mysqli->prepare("DELETE FROM short_answer WHERE question_num = ?")){

			echo "Prepare Failed";

			$mysqli->close();

			die();

		}

	}else if($_POST['type'] == 'mc'){

		if(!$stmt = $mysqli->prepare("DELETE FROM multiple_choice WHERE question_num = ?")){

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