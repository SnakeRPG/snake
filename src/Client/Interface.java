package Client;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Interface extends JFrame implements WindowListener {

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

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * A la fermeture on deconnecte l'user
	 */
	@Override
	public void windowClosed(WindowEvent arg0) {

		ServerLink sl = ServerLink.getInstance();
		
		try {
			sl.logout();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
