package Client;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Interface extends JFrame {

	static Interface instance;

	private Interface()	 {

        initUI();
    }
	
	private void initUI() {
		setTitle("ShnakeRPG");
	    // Taille de la frame
	    setSize(800, 600);
	    // Placer au centre de l'ecran
	    setLocationRelativeTo(null);
	    // Resizable ou non
	    setResizable(true);
	    // On close, logout user
	    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    addWindowListener( new java.awt.event.WindowAdapter() {
	    	@Override
	        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
	    		ServerLink sl = ServerLink.getInstance();
	    		try {
	    			sl.logout();
	    		} catch (IOException e) {
	    			JOptionPane.showMessageDialog( null, e.getMessage(), "Error",  JOptionPane.ERROR_MESSAGE);
	    		}
	    		
	    		System.exit(0);
	        }
	    });
        
        initElement();
        
        setVisible(true);
	}

	
    private void initElement() {
    	Pan_login pan_login = Pan_login.getInstance();
    	
    	this.setPanel(pan_login);
	}
    
    public void setPanel( JPanel pan ) {
    	this.setContentPane(pan);
    	this.revalidate();
    }

    static public Interface getInstance() {
    	if ( instance == null ) {
    		instance = new Interface();
    	}
    	return instance;
    }
    
	public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Interface.getInstance();
            }
        });
    }

}
