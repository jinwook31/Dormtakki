<?php
	$link = mysql_connect("localhost", "ID", "PW") or die ("Fail to connect MySQL");
	$select_db = mysql_select_db("dbname", $link) or die ("Fail to connect DB");
	
	$val = $_GET["val"]; $ID = $_GET["ID"];
	
	$sqlt = "insert into $ID (val, time) values ('$val','Now())')";
	$db_result = mysql_query($sqlt, $link) or die ("Fail to connect Table");
	
	echo "Success!";
?>
