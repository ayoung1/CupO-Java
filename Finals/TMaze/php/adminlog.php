<?php
	session_start();
	
	require "password.php";
	include "creds.php";
	
	$username = $_POST['username'];
	$password = $_POST['pass'];
	
	if(strlen($username) === 0)
	{
		die();
	}
	
	if(strlen($password) === 0)
	{
		die();
	}
	
	$mysqli = new mysqli($server, $user, $pw, $db);
	
	if(!($stmt = $mysqli->prepare("SELECT user_password FROM users  WHERE user_name=?")))
	{
		echo "Prepare Failed";
		$mysqli->close();
		die();
	}
	
	if(!$stmt->bind_param("s", $username))
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
	
	$stmt->bind_result($hash);
	$stmt->fetch();
	
	if(password_verify($password, $hash))
	{
		$_SESSION['user'] = $username;
		$_SESSION['timeout'] = time();
		echo "Success";
	} else {
		echo "Username and Password don't match";
	}
	
	$stmt->free_result();
	$stmt->close();
	$mysqli->close();
?>