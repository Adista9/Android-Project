<?php
	/* ETAPE 0 : INCLUDE DE FONCTIONS ET PARAMETRAGE */
	$GLOBALS['json']=0;
	include('inc/erreurs.inc');
	


	/* ETAPE 1 : TEST DES PARAMETRES */
	if (!isset($_POST['pseudo']) || empty($_POST['pseudo'])){
		RetournerErreur(100);
	}
	if (!isset($_POST['mdp']) || empty($_POST['mdp'])){
		RetournerErreur(110);
	}
	$pseudo = $_POST['pseudo'];
	$mdp = $_POST['mdp'];
	

	
	/* ETAPE 2 : CONNEXION A LA BASE DE DONNEES */
	include('inc/db.inc');



	/* ETAPE 3 : VERIF LOGIN/PASSWORD */
	try{	
		$requete="SELECT id_utilisateur FROM utilisateurs
				  WHERE pseudo=? AND mdp=?";
		$stm= $bdd->prepare($requete);
		$stm->execute(array($pseudo, $mdp));
		$row = $stm->fetch();
	}catch(Exception $e){
		erreur(2001);
	}
	


	/* ETAPE 4 : VERIFICATION DE LA COMBINAISON LOGIN/PASSWORD */
	if(count($row) == 1){
		RetournerErreur(200);
	}



	/* ETAPE 5 : SI ON EST ARRIVE JUSQU'ICI, C'EST QUE TOUT EST CORRECT */
	echo '{"code": 0,"id":'.$row[id_utilisateur].'}';


	/* Valeurs de retour
	 * 00 : OK
	 * 100 : problème $_POST['pseudo']
	 * 110 : problème $_POST['mdp']
	 * 200 : combinaison login/password incorrecte
	 * 1000 : problème de connexion �  la DB
	 * 20XX : autre problème
	 */
?>
