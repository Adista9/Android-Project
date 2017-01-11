<?php
	/* ETAPE 0 : TINCLUDE DE FONCTIONS ET PARAMETRAGE */
	$GLOBALS['json']=1;
	include('inc/erreurs.inc');

	/* ETAPE 1 : CONNEXION A LA BASE DE DONNEES */
	include('inc/db.inc');



	/* ETAPE 2 : RECUPERATION DE LA LISTE DES JEUX, AINSI QUE LE SCORE ET LE PSEUDO DU PREMIER */
	// 2.1. On exécute la requête
	try{	
		$requete="SELECT jeu, pseudo, MAX(score) AS max_score FROM scores S
					INNER JOIN utilisateurs U ON U.id_utilisateur = S.id_utilisateur
					GROUP BY jeu;";
		$stm= $bdd->prepare($requete);
		$stm->execute();	
	}catch(Exception $e){
		RetournerErreur(2003);
	}

	// 2.2. On vérifie si on a trouvé au moins un jeu
	if($row = $stm->fetch()){
		$chaine_jeux = '{"nom_jeu": "' . $row["jeu"] . '", "pseudo": "' . $row["pseudo"] . '", "score": "' . $row["max_score"] . '"}';
	}else{
		RetournerErreur(300);
	}

	// 2.3. On construit le fichier JSON des jeux
	while ($row = $stm->fetch()) {
                $chaine_jeux .= ', {"nom_jeu": "' . $row["jeu"] . '", "pseudo": "' . $row["pseudo"] . '", "score": "' . $row["max_score"] . '"}';
	}


	/* ETAPE 3 : SI ON EST ARRIVE JUSQU'ICI, C'EST QUE TOUT EST CORRECT */
	$resultat='{ "code": 0, "jeux": [' . $chaine_jeux . '] }';
	echo $resultat;


	/* Valeurs de retour
	 * 00 : OK
	 * 300 : Aucun jeu trouvé
	 * 1000 : problème de connexion �  la DB
	 * 20XX : autre problème
	 */
?>
