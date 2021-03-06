package Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONObject;

public class ClientReader extends Thread {
	
	Socket socket;
	long id;

	public ClientReader(Socket socket, long id) throws IOException {
		this.socket = socket;
		this.id = id;
	}

    public void run() {

        try {
			ServerTreatment message_interpreter = new ServerTreatment();
	        BufferedReader in = new BufferedReader( new InputStreamReader( this.socket.getInputStream() ) );
	        PrintWriter out;
	        boolean running = true;
        
            while(running) {

        		// Récupère le message
        		String message = in.readLine();
        		System.out.println("#RECEIVED: "+message);
        		
        		// Traitement
        		String treated_message = message_interpreter.treat(message, id);
        		System.out.println(treated_message);
        		
        		// Répond au client
        		out = new PrintWriter( socket.getOutputStream() );
        		out.println(treated_message);
        		out.flush();
        		
        		// On regarde si l'user s'est déconnecté, si oui on ferme le socket
        		String username = new JSONObject( message ).getJSONObject( TreatmentController.ID_HEADER ).getString( TreatmentController.ID_USERNAME );
        		if ( ! UserLogged.getInstance().contains( username ) ) {
        			System.out.println("Connection client "+id+" closed");
        			running = false;
        		}
            }
            
    		// Ferme connection
    		socket.close();
    		in.close();
            
        } catch(IOException e) {
            e.printStackTrace();
        }
        
    }

}
