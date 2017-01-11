package eu.tankernn.julklapp;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Julspel {
	public static final String[] NAMES = { "Claes", "Lars", "Fredrik", "Rune-Bertil", "Pepita" }, GIFTS = {"Nalle", "PS4", "PC", "Spel", "Lego"};

	private Random rand = new Random();

	private JFrame frame;
	private JComboBox<Difficulty> difficultyBox = new JComboBox<Difficulty>(Difficulty.values());
	private JButton showAllBtn = new JButton("Visa namn och klappar."), startBtn = new JButton("Starta spelet!");

	private int difficulty = 3;

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

		frame = new JFrame();
		frame.setLayout(new GridLayout(3, 1));

		difficultyBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				difficulty = ((Difficulty) e.getItem()).pairs;
			}
		});
		frame.add(difficultyBox);
		showAllBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayLists(NAMES, GIFTS);
			}
		});
		frame.add(showAllBtn);
		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startGame();
			}
		});
		frame.add(startBtn);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void startGame() {
		associations = generateAssociations();

		Iterator<Entry<String, String>> entries = associations.entrySet().iterator();

		for (int i = 0; i < difficulty - 1; i++) {
			Entry<String, String> e = entries.next();
			JOptionPane.showMessageDialog(frame, e.getKey() + " får " + e.getValue() + ".");
		}

		Entry<String, String> secret = entries.next();
		String guessName = JOptionPane.showInputDialog(frame, "Vem är kvar?").trim().toLowerCase();
		String guessGift = JOptionPane.showInputDialog(frame, "Vad får " + guessName + "?").trim().toLowerCase();
		String correctName = secret.getKey().toLowerCase();
		String correctGift = secret.getValue().toLowerCase();
		String response;
		if (guessName.equals(correctName) && guessGift.equals(correctGift))
			response = "Grattis! Du hade alla rätt!";
		else if (guessName.equals(correctName))
			response = "Rätt namn, men fel klapp. Rätt klapp var " + correctGift + ".";
		else if (guessGift.equals(correctGift))
			response = "Rätt klapp, men fel namn. Rätt namn var " + secret.getKey() + ".";
		else
			response = "Tyvärr, inga rätt. Rätt svar var: " + secret.getKey() + " får " + secret.getValue() + ".";
		
		JOptionPane.showMessageDialog(frame, response);
	}

	private Map<String, String> generateAssociations() {
		Map<String, String> assoc = new HashMap<String, String>();
		List<String> names = new ArrayList<String>(Arrays.asList(NAMES)), gifts = new ArrayList<String>(Arrays.asList(GIFTS));
		
		names = randomFilter(names, difficulty);
		gifts = randomFilter(gifts, difficulty);
		displayLists(names.toArray(new String[names.size()]), gifts.toArray(new String[names.size()]));
		
		while (!names.isEmpty()) {
			assoc.put(names.remove(rand.nextInt(names.size())), gifts.remove(rand.nextInt(gifts.size())));
		}
		return assoc;
	}
	
	/**
	 * Removes elements from the list randomly to match the new size.
	 * @param list The list to filter
	 * @param newSize The target length of the list
	 * @return The new list
	 */
	private List<String> randomFilter(List<String> list, int newSize) {
		while (list.size() > newSize) {
			list.remove(rand.nextInt(list.size()));
		}
		return list;
	}

	private void displayLists(String[] names, String[] gifts) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<h3>Namn:</h3><ul>");

		for (String name : names) {
			sb.append("<li>").append(name).append("</li>");
		}

		sb.append("</ul><h3>Klappar:</h3><ul>");

		for (String gift : gifts) {
			sb.append("<li>").append(gift).append("</li>");
		}

		sb.append("</ul></html>");

		JOptionPane.showMessageDialog(frame, sb);
	}

}
