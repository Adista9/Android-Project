<?php
	function RetournerErreur($code_erreur){
		if($GLOBALS['json']==0){
			echo $code_erreur;
		}else{
			echo "{\"code\": $code_erreur}";

		}
		exit();
	}
?>