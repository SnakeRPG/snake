package Server;

import java.sql.SQLException;

import org.json.JSONObject;

abstract public class TreatmentController {

	// ID dans le json envoyé
	static final String ID_RESPONSE = "response";
	// Valeur des codes de retour
	static final int CODE_REQUEST_DONE = 1;
	static final int CODE_REQUEST_NOT_DONE = 2;
	static final int CODE_REQUEST_UNRECOGNIZE = 3;
	static final int CODE_MDP_NON_WELL_FORMED = 4;
	static final int CODE_BDD_FAILURE = 5;
	static final int CODE_USER_ALREADY_LOGGED = 6;
	static final int CODE_USER_WRONG_USERNAME = 7;
	static final int CODE_USER_WRONG_MDP = 8;
	static final int CODE_IDCLIENT_DIFFERENT = 9;
	
	// ID dans le json reçu
	static final String ID_HEADER = "header";
	static final String ID_TYPE = "typeEvent";
	static final String ID_USERNAME = "username";
	static final String ID_IDCLIENT = "idClient";
	static final String ID_DATA = "data";
	
	DataManager dataManager;
	
	public TreatmentController () {
		dataManager = DataManager.getInstance();
	}

	public JSONObject treatJson( String username, JSONObject json_data) {
		JSONObject jsonTreatedMessage = new JSONObject();
		
		try {
			// On tente de se connecter à la base
			this.dataManager.connect();

			
			// On execute un traitement particulier suivant le controller
			// Cette fonction va modifier le JSON à renvoyer (jsonTreatedMessage)
			specificTreatment(jsonTreatedMessage, username, json_data);
			
			
			// On tente de se déconnecter de la base
			this.dataManager.disconnect();
			
		} catch (SQLException | ClassNotFoundException e) {
			// On informe que la requête n'a pas été effectuée
			jsonTreatedMessage.accumulate( TreatmentController.ID_RESPONSE, TreatmentController.CODE_BDD_FAILURE );
			e.printStackTrace();
		}
		
		// On renvoit la chaine de caractères du json
		return jsonTreatedMessage;
	}

	protected abstract void specificTreatment( JSONObject jsonTreatedMessage, String username, JSONObject json_data) throws SQLException;
	
}
