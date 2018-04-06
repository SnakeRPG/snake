package Client;

import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TESTClient {
	private Socket socket              = null;
	   private Scanner  console   = null;
	   private DataOutputStream streamOut = null;
	   private DataInputStream streamIn =  null;

	   public TESTClient(String serverName, int serverPort) {
	      try
	      {  socket = new Socket(serverName, serverPort);
	         start();
	      }
	      catch(UnknownHostException uhe)
	      {  System.out.println("Host unknown: " + uhe.getMessage());
	      }
	      catch(IOException ioe)
	      {  System.out.println("Unexpected exception: " + ioe.getMessage());
	      }
	      String line = "";
	      try {
				/**
				 * pas d'inscription via l'appli
				 * String mess_registration = "{'header':{'username':'USERNAME', 'ipPlayer':'IPPLAYER', 'typeEvent':'registration'}, 'data':{'mdp':'MDP', 'mail':'user@user.com'}}";
				 */
				String mess_login = "{'header':{'username':'USERNAME', 'ipPlayer':'IPPLAYER', 'typeEvent':'authentication'}, 'data':{'connect':'true', 'mdp':'123456'}}";
				String mess_logout = "{'header':{'username':'USERNAME', 'ipPlayer':'IPPLAYER', 'typeEvent':'authentication'}, 'data':{'connect':'false'}}";
				String mess_skill_add = "{'header':{'username':'Louis', 'ipPlayer':'IPPLAYER', 'typeEvent':'skill'}, 'data':{'idSnake':'1', 'idSkill':'1', 'add':'true'}}";
				String mess_game_left = "{'header':{'username':'USERNAME', 'ipPlayer':'IPPLAYER', 'typeEvent':'game'}, 'data':{'idKey':'"+ KeyEvent.VK_DOWN +"'}}";
				String message = "{'header':{'username':'USERNAME', 'idClient':'5', 'typeEvent':'authentication'}, 'data':{'connect':'false'}}";
	            streamOut.writeUTF(message);
	            streamOut.flush();
	            while (line.equals("")) {
	            line = streamIn.readUTF();
	            System.out.println(line);
	            }
	         }
	         catch(IOException ioe)
	         {  System.out.println("Sending error: " + ioe.getMessage());
	         }
	   }
	   public void start() throws IOException
	   {  console   = new Scanner(System.in);
	      streamOut = new DataOutputStream(socket.getOutputStream());
		  streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
	   }
	   public void stop()
	   {  try
	      {  if (console   != null)  console.close();
	         if (streamOut != null)  streamOut.close();
	         if (socket    != null)  socket.close();
	      }
	      catch(IOException ioe)
	      {  System.out.println("Error closing ...");
	      }
	   }
	   public static void main(String args[])
	   {
		   TESTClient client = null;
		   String host = "127.0.0.1";
		   int port = 2020;
		   client = new TESTClient(host, port);
	   }
}
