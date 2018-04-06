package Server;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

/**
 * Classe permettant de faire le lien entre le programme et la BDD
 * @author teddy
 */
public class DataManager {

	public static DataManager instance;

	public static String table_player = "Player";
		public static String field_player_password = "PassWord";
		public static String field_player_username = "UserName";
	
	public static String table_skill = "Skill";
		public static String field_skill_id = "idSkill";
	
	public static String table_masteredskill = "MasteredSkill";
		public static String field_masteredskill_snake = "fk_idSnake";
		public static String field_masteredskill_skill = "fk_idSkill";
		
	public static String table_snake = "Snake";
	
	private String url;
	private String user;
	private String mdp;
	private String db;
	private Connection connector;
	
	private DataManager() {
		String address = "127.0.0.1";
		user = "UA-user";
		mdp = "ua-user";
		db = "SnakeRPG_Schema";
		this.url = "jdbc:mysql://"+ address +"/"+ this.db;
	}
	
	/**
	 * Permet de lancer une connexion à la BDD
	 * @param adresse - L'ip ou hostname de la BDD
	 * @param usr - Le nom d'utilisateur de la BDD
	 * @param db - Le nom de la base
	 * @param mdp - Le mdp de l'utilisateur de la BDD
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void connect() throws ClassNotFoundException, SQLException{
		this.connector = DriverManager.getConnection(this.url, this.user, this.mdp);
	}
	
	/**
	 * Permet de fermer la connexion
	 * @throws SQLException
	 */
	public void disconnect() throws SQLException{
		// stmt.close(); -> est fermé après chaque utilisation
		connector.close();
	}

	/**
	 * Ajoute une compétence à un snake
	 * @param idSnake
	 * @param idSkill 
	 * @throws SQLException 
	 */
	public boolean addSkill ( String idSnake, String idSkill ) throws SQLException {
		Statement stmt = connector.createStatement();
		String request = String.format( "INSERT INTO %s (%s,%s) VALUES ('%s','%s');", table_masteredskill, field_masteredskill_snake, field_masteredskill_skill, idSnake, idSkill );
		System.out.println( request );
		boolean ret = stmt.execute(request);
		stmt.close();
		
		return ret;
	}

	/**
	 * Enlève une compétence d'un snake
	 * @param idSnake
	 * @param idSkill 
	 * @throws SQLException 
	 */
	public boolean removeSkill ( String idSnake, String idSkill ) throws SQLException {
		Statement stmt = connector.createStatement();
		String request = String.format( "DELETE FROM %s WHERE %s = '%s' AND %s = %s;", table_masteredskill, field_masteredskill_snake, idSnake, field_masteredskill_skill, idSkill );
		System.out.println( request );
		boolean ret = stmt.execute(request);
		stmt.close();
		
		return ret;
	}

	/**
	 * Récupère les compétences d'un snake
	 * @param idSnake
	 * @throws SQLException 
	 */
	public boolean getSkills ( String idSnake ) throws SQLException {
		Statement stmt = connector.createStatement();
		String request = String.format("SELECT * FROM %s masteredSkill JOIN %s skill ON masteredSkill.%s = skill.%s WHERE masteredSkill.%s = %s", table_masteredskill, table_skill, field_masteredskill_skill, field_skill_id, field_masteredskill_snake, idSnake);
		System.out.println( request );
		ResultSet rs = stmt.executeQuery( request );
		System.out.println("Snake n°"+idSnake);
		while ( rs.next() ) {
			System.out.println("\t"+rs.getString("Name"));
		}
		boolean ret = stmt.execute(request);
		stmt.close();
		
		return ret;
	}

	/**
	 * ON NE PEUT PAS CREER DE COMPTE DIRECTEMENT VIA L'APPLI, IL FAUT PASSER PAR LE SITE (mais il vaut mieux garder la fonction au cas où)
	 * Ajoute un nouveau joueur en base
	 * @param username
	 * @param mdp 
	 * @param mail 
	 * @throws SQLException 
	 */
	public boolean register ( String username, String mdp, String mail ) throws SQLException {
		Statement stmt = connector.createStatement();
		String mdp_hashed = HashPassword.get_SHA_512_SecurePassword(mdp);
		String request = String.format( "INSERT INTO %s VALUES ('%s','%s', '%s', 0, 0);", table_player, username, mdp_hashed, mail );
		System.out.println( request );
		boolean ret = stmt.execute(request);
		stmt.close();
		
		return ret;
	}
	
	/**
	 * Connecte le joueur
	 * @param username
	 * @param mdp 
	 * @throws SQLException 
	 */
	public int login ( String username, String mdp ) throws SQLException {
		UserLogged ul = UserLogged.getInstance();
		int ret = TreatmentController.CODE_REQUEST_NOT_DONE;
		
		// Si l'utilisateur est déjà connecté
		if ( ul.contains( username ) ) {
			System.out.println("user "+ username + " déjà co");
			ret = TreatmentController.CODE_USER_ALREADY_LOGGED;
		} else {
			// On vérifie le mdp
			Statement stmt = connector.createStatement();
			String request = String.format("SELECT %s FROM %s WHERE %s = '%s'", field_player_password, table_player, field_player_username, username);
			ResultSet rs = stmt.executeQuery( request );

			System.out.println(request);
			
			// Si on a trouvé un élément correspondant à la requête
			if ( rs.next() ) {
				String mdp_bdd = rs.getString("password");
				
				// On hash le mdp récupéré pour le comparer avec celui reçu
				String mdp_hashed = HashPassword.get_SHA_512_SecurePassword(mdp);
				
				// Si les mdp sont égaux, on connecte l'utilisateur
				if ( mdp_bdd.equals( mdp_hashed ) ) {
					ul.add(username);
					ret = TreatmentController.CODE_REQUEST_DONE;
//						System.out.println("Login: "+ username +", mdp: "+ mdp);
				} else {
					ret = TreatmentController.CODE_USER_WRONG_MDP;
				}
			} else {
				ret = TreatmentController.CODE_USER_WRONG_USERNAME;
			}
			
			
						
		}
		
		return ret;
	}

	/**
	 * Déconnecte le joueur
	 * @param username
	 */
	public int logout ( String username ) {
		UserLogged ul = UserLogged.getInstance();
		int ret = TreatmentController.CODE_REQUEST_NOT_DONE;
		
		// On regarde si l'utilisateur est connecté
		if ( ul.contains( username ) ) {

			System.out.println("Logout: "+ username);
			ul.remove( username );
			ret = TreatmentController.CODE_REQUEST_DONE;
			
		} else {
			ret = TreatmentController.CODE_USER_NOT_LOGGED;
		}
		
		return ret;
	}
	
	/**
	 * Récupère l'instance unique de la classe DataManager
	 * @return
	 */
	public static DataManager getInstance() {
		if ( instance == null ) {
			instance = new DataManager();
		}
		return instance;
	}

}
