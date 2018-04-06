package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONObject;

import Server.TreatmentController;

public class Pan_login extends JPanel {

	public static Pan_login instance;
	
	//

	// LOGIN
	private JLabel l_login;
	private JLabel l_mdp;
	private JTextField tf_login;
	private JTextField tf_mdp;
	private JButton b_connexion;

	private Pan_login() {

		b_connexion = new JButton("Connexion");
		l_login = new JLabel("Login:");
		l_mdp = new JLabel("Password:");
		tf_login = new JTextField();
		tf_mdp = new JTextField();
		
		initUI();
	}

	private void initUI() {

		setLayout( new BoxLayout(this, BoxLayout.Y_AXIS));

    	b_connexion.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				ServerLink sl = ServerLink.getInstance();
				try {
					// On définit l'username pour les requêtes					
					String username = tf_login.getText();
					sl.setUsername( tf_login.getText() );
					
					int codeReturned = sl.login( username, tf_mdp.getText());
					Interface ihm = Interface.getInstance();
					
					if ( codeReturned == ServerLink.CODE_REQUEST_DONE ) {
						
						// On change de panel
				    	Pan_menu pan = Pan_menu.getInstance();
						ihm.setPanel(pan);
												
					} else {
						JOptionPane.showMessageDialog( null, ErrorTranslator.translate(codeReturned), "Error",  JOptionPane.ERROR_MESSAGE);
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
    	
    	tf_login.setPreferredSize( new Dimension(100, 30) );
    	tf_mdp.setPreferredSize( new Dimension(100, 30) );
    	
    	l_login.setLabelFor(tf_login);
    	l_mdp.setLabelFor(tf_mdp);
    	
    	add(l_login);
    	add(tf_login);
    	add(l_mdp);
    	add(tf_mdp);
    	add(b_connexion);

	}
	
	public static Pan_login getInstance() {
		if ( instance == null ) {
			instance = new Pan_login();
		}
		return instance;
	}
}
