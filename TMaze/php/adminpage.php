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
		<link rel="stylesheet" href="../css/foundation.css" />
		<link rel="stylesheet" href="../css/UnitTest/QUnit.css" />
		<link rel="stylesheet" href="../css/styles.css" />
		<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
		<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
		<script src="../js/vendor/modernizr.js"></script>
		<script src="../js/MyScripts.js"></script>
	</head>
	
	<body>
		<?php if(isset($_SESSION['user'])) :?>
			<div class="wrapper">
				<a id="logout" class="button tiny radius" href='logout.php'>Logout</a> 
				<div class="row">
					<div class="small-12 columns vertical-space">
						<h1 class="center medium-8 large-6 medium-offset-3">Hello <?php echo $_SESSION['user'] ?></h1>
					</div>
				</div>
				
				<div id='questiontables' class='row'>
					<div id='tableradios' class='medium-10 medium-offset-2 vertical-space'>
						<input id='radio1' class='medium-offset-1' type="radio" name="type" value="truefalse"/><label for='radio1'>True/False</label>
						<input id='radio2' class='medium-offset-1' type="radio" name="type" value="oneword"/><label for='radio2'>Short Answer</label>
						<input id='radio3' class='medium-offset-1' type="radio" name="type" value="multiplechoice"/><label for='radio3'>Multiple Choice</label>
					</div>
					
					<table id='truefalse' class='vertical-space medium-12 hide'>
						<tr class="header">
							<td><p>Question</p></td>
							<td><p>Answer</p></td>
							<td class='wrap-content'><p>Delete</p></td>
						</tr>
						<?php include 'tables/tftable.php'; ?>
					</table>
					<table id='oneword' class='vertical-space medium-12 hide'>
						<tr class="header">
							<td><p>Question</p></td>
							<td><p>Answer</p></td>
							<td class='wrap-content'><p>Delete</p></td>
						</tr>
						<?php include 'tables/satable.php'; ?>
					</table>
					<table id='multiplechoice' class='vertical-space medium-12 hide'>
						<tr class="header">
							<td><p>Question</p></td>
							<td><p>Answer</p></td>
							<td><p>Opt 1</p></td>
							<td><p>Opt 2</p></td>
							<td><p>Opt 3</p></td>
							<td><p>Opt 4</p></td>
							<td class='wrap-content'><p>Delete</p></td>
						</tr>
						<?php include 'tables/mctable.php'; ?>
					</table>
				</div>
			</div>
			
		<?php else :?>
			<p>Please Login <a href='../index.html'>here</a></p>
		<?php endif; ?>
	</body>
</html>