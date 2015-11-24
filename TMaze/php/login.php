<?php
	session_start();

	require 'password/password.php';
	include "creds.php";
	
	$username = $_POST["username"];
	$password = $_POST["password"];
	
	
	if(strlen($username) === 0)
	{
		die();
	}
	
	if(strlen($password)===0)
	{
		die();
	}
	
	$mysqli = new mysqli($server, $user, $pw, $db);
	
	if(!($stmt = $mysqli->prepare("SELECT password, canblast, admin, companyid FROM users  WHERE username=?")))
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
	
	if($stmt->execute())
	{
		$stmt->bind_result($hash, $canblast, $admin, $companyid);
		$stmt->fetch();
		if(password_verify($password, $hash))
		{
			$_SESSION['user'] = $username;
			$_SESSION['canblast'] = $canblast;
			$_SESSION['admin'] = $admin;
			$_SESSION['companyid'] = $companyid;
			$_SESSION['timeout'] = time();
			echo "Success";
		}
	}else{
		echo "Fail";
		$mysqli->close();
		die();
	}
	
	$stmt->free_result();
	$stmt->close();
	$mysqli->close();

?>