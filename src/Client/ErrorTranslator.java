package Client;

public class ErrorTranslator {

	public static String translate ( int code ) {
		String message = null;
		
		switch ( code ) {
		
			case ServerLink.CODE_BDD_FAILURE:
				message = "Nous avons rencontré un problème avec la  base de données. Vérifiez les informations envoyées.";
				break;
				
			case ServerLink.CODE_REQUEST_UNRECOGNIZE:
				message = "Requête non reconnu.";
				break;
				
			case ServerLink.CODE_REQUEST_NOT_DONE:
				message = "Requête non effectué.";
				break;
				
			case ServerLink.CODE_MDP_NON_WELL_FORMED:
				message = "Votre mot de passe est mal formé.";
				break;
				
			case ServerLink.CODE_USER_ALREADY_LOGGED:
				message = "Vous tenté de connecter un utilisateur déjà connecté.";
				break;
				
			case ServerLink.CODE_USER_WRONG_USERNAME:
				message = "Aucun user avec ce username.";
				break;
				
			case ServerLink.CODE_USER_WRONG_MDP:
				message = "Mauvais mot de passe.";
				break;
				
			case ServerLink.CODE_IDCLIENT_DIFFERENT:
				message = "Il semblerait que la requête soit une tentative d'usurpation d'identité ...";
				break;
		}
		
		return message;
	}
}
