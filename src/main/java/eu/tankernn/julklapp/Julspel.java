package eu.tankernn.julklapp;

import java.awt.EventQueue;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Julspel {
	public static final String[] NAMES = {"Claes", "Lars"}, GIFTS = {};

	private Random rand = new Random();

	private JFrame frame;

	private int difficulty;

	private Map<String, String> associations = new HashMap<>();

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
		List<String> names = Arrays.asList(NAMES), gifts = Arrays.asList(GIFTS);
		for (int i = 0; i < difficulty; i++) {
			associations.put(names.remove(rand.nextInt(names.size())), gifts.remove(rand.nextInt(gifts.size())));
		}
	}
	
	private void showAll() {
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<h1>Names:</h1>");
		
		for (String name : NAMES) {
			sb.append(name).append("<br>");
		}
		
		sb.append("<h1>Gifts:</h1>");
		
		for (String gift : GIFTS) {
			sb.append(gift).append("<br>");
		}
		
		sb.append("</html>");
		
		JOptionPane.showMessageDialog(frame, sb);
	}

}
