package Client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONObject;

public class ServerLink {

	// ID dans le json reçu
	static final String ID_RESPONSE = "response";
	static final String ID_IDCLIENT = "idClient";
	
	// Valeur des codes de retours
	static final int CODE_REQUEST_DONE = 1;
	static final int CODE_REQUEST_NOT_DONE = 2;
	static final int CODE_REQUEST_UNRECOGNIZE = 3;
	static final int CODE_MDP_NON_WELL_FORMED = 4;
	static final int CODE_BDD_FAILURE = 5;
	static final int CODE_USER_ALREADY_LOGGED = 6;
	static final int CODE_USER_WRONG_USERNAME = 7;
	static final int CODE_USER_WRONG_MDP = 8;
	static final int CODE_IDCLIENT_DIFFERENT = 9;
	static final int CODE_USER_NOT_LOGGED = 10;
	
	static ServerLink instance;
	
	// User infos
	private String username;
	private long idClient;
	
	private Socket socket;
	private PrintWriter streamOut;
	private BufferedReader streamIn;
	
	
	public int login( String username, String mdp ) throws IOException {

        // On connecte le socket
        connect();
        
		String message = JSONConstructor.login(this.username, this.idClient, mdp);
        streamOut.println(message);
        streamOut.flush();

	    String line = "";
	    String receivedMessage = "";
        while (line.equals("")) {
	        line = this.streamIn.readLine();
	        receivedMessage += line;
	        System.out.println(line);
        }
        
        JSONObject receivedJson = new JSONObject( receivedMessage );
        int code = receivedJson.getInt( ID_RESPONSE );

		// On donne le codeClient au ServerLink pour les requêtes si nous en a envoyé un et que le code de retour est bon
        if ( receivedJson.has( ID_IDCLIENT ) && code == CODE_REQUEST_DONE ) {
        	this.idClient = receivedJson.getLong( ID_IDCLIENT );
        }
		
        return code;
	}

	public int logout() throws IOException {
		int code = CODE_REQUEST_NOT_DONE;
		
		if ( this.username != null ) {
			String message = JSONConstructor.logout(this.username, this.idClient);
	        streamOut.println(message);
	        streamOut.flush();
	
		    String line = "";
		    String receivedMessage = "";
	        while (line.equals("")) {
		        line = this.streamIn.readLine();
		        receivedMessage += line;
		        System.out.println(line);
	        }
	        
	        JSONObject receivedJson = new JSONObject( receivedMessage );
	        code = receivedJson.getInt( ID_RESPONSE );
	        
	        // On déconnecte le socket
	        disconnect();
		}
        return code;
	}
	
	public void connect() {
		try {
			this.socket = new Socket("127.0.0.1", 2020);
			this.streamOut = new PrintWriter(socket.getOutputStream());
	        this.streamIn = new BufferedReader( new InputStreamReader( this.socket.getInputStream() ) );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			this.streamIn.close();
			this.streamOut.close();
			this.socket.close();
			this.username = null;
			this.idClient = 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setUsername( String username ) {
		this.username = username;
	}
	
	static ServerLink getInstance() {
		if ( instance == null ) {
			instance = new ServerLink();
		}
		return instance;
	}

}
