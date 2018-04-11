package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Pan_menu extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public static final String URL_LIST_SNAKES = "www.google.com";
	
	public static Pan_menu instance;
	
	private JPanel current_panel;
	
	private JPanel pan_buttons;
	private JButton b_play;
	private JButton b_snakes;
	private JButton b_logout;
	
	private Pan_menu() {
		setLayout(new BorderLayout());
		
		pan_buttons = new JPanel();
		b_play = new JButton("Jouer");
		b_snakes = new JButton("Snakes");
		b_logout = new JButton("Deconnexion");
		
		initUI();
	}
	
	private void initUI() {
		
		pan_buttons.setLayout(new GridLayout(5,1) );
		
		b_play.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {

				JPanel pan = new JPanel();
				pan.add(new JTextField("JE VEUX JOUER"));
				Pan_menu.getInstance().setPanel(pan);
				
			}
		});

		b_snakes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				openUrl( URL_LIST_SNAKES );

			}
		});
		
		b_logout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ServerLink sl = ServerLink.getInstance();
				try {
					sl.logout();
				} catch (IOException e) {
					JOptionPane.showMessageDialog( null, e.getMessage(), "Error",  JOptionPane.ERROR_MESSAGE);
				}
				Interface.getInstance().setPanel(Pan_login.getInstance());				
			}
		});

		pan_buttons.add(b_play);
		pan_buttons.add(b_snakes);
		pan_buttons.add(b_logout);

		this.add(pan_buttons, BorderLayout.WEST);
//		this.add(Pan_article.getInstance(), BorderLayout.CENTER);
	}
	
	private void openUrl( String url ) {
		// Browser commun
		String[] browsers = { "firefox", "opera", "mozilla", "netscape" };
		String browser = null;
		
		try {
			for (int count = 0; count < browsers.length && browser == null; count++) {
				if (Runtime.getRuntime().exec(new String[] { "which", browsers[count] }).waitFor() == 0) {
					// have found a browser
					browser = browsers[count];
				}
			}
			// open using a browser
			Runtime.getRuntime().exec(new String[] { browser, url });
			
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setPanel( JPanel pan ) {
		if ( this.current_panel != null ) {
			this.remove(this.current_panel);
		}
		this.add(pan, BorderLayout.CENTER);
		this.current_panel = pan;
		this.validate();
	}
	
	public static Pan_menu getInstance() {
		if ( instance == null ) {
			instance = new Pan_menu();
		}
		return instance;
	}
}
