package Server;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class ServerTreatment {
	
	private TreatmentController controller;
	private ControllerFactory controllerFactory;
	
	public ServerTreatment() {
		controllerFactory = new ControllerFactory();
	}

	public String treat( String message, long id ) {

		String res = ResponseConstructor.create( TreatmentController.CODE_REQUEST_NOT_DONE );
		
		// Si le JSON est invalide
		if ( ! this.isValidJson(message) ) {
			res = ResponseConstructor.create( TreatmentController.CODE_REQUEST_UNRECOGNIZE );
		} else {
			
			// On récupère les objets JSON
			JSONObject json_object = new JSONObject( message );
			JSONObject json_header = json_object.getJSONObject( TreatmentController.ID_HEADER );
			JSONObject json_data= json_object.getJSONObject( TreatmentController.ID_DATA );
			String type = json_header.getString( TreatmentController.ID_TYPE );
			try {
				
				// Si l'id du client correspond à celui qu'il y a dans la requête ou qu'il demande l'identification, on peut la traiter
				long idClient = json_header.getLong( TreatmentController.ID_IDCLIENT );
				if ( type.equals( ControllerFactory.ID_AUTHENTICATION ) || id == idClient ) {
					
					// On récupère le controller adequat pour répondre à l'action
					controller = controllerFactory.create(type);
					
					// On récupère l'utilisateur
					String username = json_header.getString( TreatmentController.ID_USERNAME );
					
					// On récupère la réponse au format JSON 
					JSONObject json_res = controller.treatJson( username, json_data );
					
					// On ajoute l'id si on n'en a pas eu
					if ( idClient == 0 ) {
						json_res.accumulate(TreatmentController.ID_IDCLIENT, id);
					}
					
					// On la traduit en String
					res = json_res.toString();
					
				} else {
					res = ResponseConstructor.create( TreatmentController.CODE_IDCLIENT_DIFFERENT );
				}
			} catch (JSONException ex1) {
				// Nothing to do: res aura le code de la requete non effectuée
	        }
			
		}
		
		return res;
	}
	
	private boolean isValidJson (String text) {
	    try {
	        new JSONObject(text);
	    } catch (JSONException ex) {
	        // in case JSONArray is valid as well...
	        try {
	            new JSONArray(text);
	        } catch (JSONException ex1) {
	            return false;
	        }
	    }
	    return true;
	}
	
}