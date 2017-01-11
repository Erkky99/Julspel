package eu.tankernn.julklapp;
import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Julspel {

	private JFrame frame;
	private Map<String, String> associations = new HashMap<>();
	private int difficulty;
	
	private ArrayList<String> name = new ArrayList<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		

		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Julspel window = new Julspel();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Julspel() {
		initialize();
		
		name.add("Claes");
		name.add("Lars");
		name.add("");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		generateAssociations();
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void generateAssociations() {
		for (int i = 0; i < difficulty; i++) {
			
		}
	}

}
