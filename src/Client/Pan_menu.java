package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class Pan_menu extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public static Pan_menu instance;
	
	public Pan_menu() {
		setLayout(new BorderLayout());
		initUI();
	}
	
	private void initUI() {
		JButton but = new JButton("Deconnexion");
		but.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ServerLink sl = ServerLink.getInstance();
				try {
					sl.logout();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Interface.getInstance().setPanel(Pan_login.getInstance());				
			}
		});
//		JMenuBar menuBar = new JMenuBar();
//		this.add(menuBar, BorderLayout.NORTH);
//		
//		JMenu menu = new JMenu("Déconnexion");
//		menuBar.add(menu);

		this.add(but, BorderLayout.NORTH);
    	setBackground(Color.BLUE);
	}
	
	public static Pan_menu getInstance() {
		if ( instance == null ) {
			instance = new Pan_menu();
		}
		return instance;
	}
}
