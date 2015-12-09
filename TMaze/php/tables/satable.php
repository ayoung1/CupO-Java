<?php

	include "creds.php";

	

	$mysqli = new mysqli($server, $user, $pw, $db);

	

	if($mysqli->connect_error){

		echo("Connect failed: %s\n". mysqli_connect_error());

        die();

	}

	if(!$stmt = $mysqli->prepare("SELECT * FROM short_answer")){

		echo "Prepare Failed";

		$mysqli->close();

		die();

	}

	if(!$stmt->execute())

	{

		echo "Execute Failed";

		$mysqli->close();

		die();

	}

	

	$stmt->bind_result($id, $question, $answer);

	$stmt->store_result();

	

	while($stmt->fetch()){

		echo "<tr id='".$id."'>";

		

		echo "<td><p>".strip_tags($question)."</p></td>";

		echo "<td><p>" . strip_tags($answer) . "</p></td>";

		

		echo "<td class='wrap-content'><input type='button' name='delete' data-id='".$id."' value='Delete'></td>";

		

		echo "</tr>";

	}

	

	$mysqli->close();

?>