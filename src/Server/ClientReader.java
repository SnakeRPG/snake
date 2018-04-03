package Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientReader extends Thread {
	
	Socket socket;

	public ClientReader(Socket socket) throws IOException {
		this.socket = socket;
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
        		String treated_message = message_interpreter.treat(message);
        		System.out.println(treated_message);
        		
        		// Répond au client
        		out = new PrintWriter( socket.getOutputStream() );
        		out.println(treated_message);
        		out.flush();
        		
            }
            
    		// Ferme connection
    		socket.close();
    		in.close();
            
        } catch(IOException e) {
            e.printStackTrace();
        }
        
    }

}
