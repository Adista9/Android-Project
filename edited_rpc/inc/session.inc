<?php
	session_start();

	function tester_session(){
		if (!isset($_SESSION['id_utilisateur']))
			return 0;
		else
			return 1;
	}

	if (tester_session()==0){
		RetournerErreur(500);
	}
?>