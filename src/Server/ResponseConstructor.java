package Server;

import org.json.JSONObject;

public class ResponseConstructor {

	public static String create( int code ) {

		JSONObject json = new JSONObject();
		json.accumulate( TreatmentController.ID_RESPONSE, code );
		return json.toString();
	}
	
}
