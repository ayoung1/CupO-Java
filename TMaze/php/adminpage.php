<?php
	session_start();
	if(isset($_SESSION['timeout']))
	{
		if($_SESSION['timeout'] + 30*60 < time())
		{
			session_unset();
		}else{
			$_SESSION['timeout'] = time();
		}
	}
?>

<html class="no-js" lang="en">
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>Trivia Maze</title>
		<link rel="stylesheet" href="css/foundation.css" />
		<link rel="stylesheet" href="css/UnitTest/QUnit.css" />
		<link rel="stylesheet" href="css/styles.css" />
		<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
		<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
		<script src="js/vendor/modernizr.js"></script>
		<script src="js/MyScripts.js"></script>
	</head>
	
	<body>
		<?php if(isset($_SESSION['user'])) :?>
			<p>Logged In <a href='logout.php'>logout here</a></p>
		<?php else :?>
			<p>Please Login <a href='../index.html'>here</a></p>
		<?php endif; ?>
	</body>
</html>