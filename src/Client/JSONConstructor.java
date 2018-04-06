package Client;

public class JSONConstructor {

	// FORMAT
	static final String main_format = "{'header':{'username':'%s', 'idClient':'%s', 'typeEvent':'%s'}, 'data':%s}";
	static final String data_login_format = "{'connect':'true', 'mdp':'%s'}";
	static final String data_logout_format = "{'connect':'false'}";
	
	// TYPE EVENT
	static final String type_event_login = "authentication";
	static final String type_event_logout = "authentication";
			
	static public String login( String username, long idClient, String mdp ) {
		
		String data = String.format( data_login_format, mdp);
		
		return String.format( main_format, username, idClient, type_event_login, data);
	}
	
	static public String logout( String username, long idClient ) {
	
		return String.format( main_format, username, idClient, type_event_logout, data_logout_format);
	}
}
