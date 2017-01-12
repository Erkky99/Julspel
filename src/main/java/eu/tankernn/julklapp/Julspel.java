package eu.tankernn.julklapp;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

public class Julspel implements ActionListener {
	public static final String[] NAMES = { "Claes", "Lars", "Fredrik", "Rune-Bertil", "Pepita", "Magnus", "Kim",
			"Frans", "Erik" },
			GIFTS = { "Nalle", "PS4", "PC", "Spel", "Lego", "Pengar", "Biobiljett", "Headset", "Mus", "Tangentbord",
					"Skärm" };

	private Random rand = new Random();

	private JFrame frame;
	private JComboBox<Difficulty> difficultyBox = new JComboBox<Difficulty>(Difficulty.values());
	private JButton showAllBtn = new JButton("Visa namn och klappar."), startBtn = new JButton("Starta spelet!");

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
		
		frame.add(difficultyBox);
		showAllBtn.addActionListener(this);
		frame.add(showAllBtn);
		startBtn.addActionListener(this);
		frame.add(startBtn);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src.equals(showAllBtn))
			displayLists(NAMES, GIFTS);
		else if (src.equals(startBtn))
			startGame(generateAssociations(NAMES, GIFTS, ((Difficulty) difficultyBox.getSelectedItem()).pairs));
	}
	
	/**
	 * Start and play the game.
	 */
	private void startGame(Map<String, String> associations) {
		Iterator<Entry<String, String>> entries = associations.entrySet().iterator();

		// Iterate through all but the last entry
		for (int i = 0; i < associations.size() - 1; i++) {
			Entry<String, String> e = entries.next();
			JOptionPane.showMessageDialog(frame, e.getKey() + " får " + e.getValue() + ".");
		}

		// Get the last entry
		Entry<String, String> secret = entries.next();

		// Prompt user
		String guessName = JOptionPane.showInputDialog(frame, "Vem är kvar?").trim().toLowerCase();
		String guessGift = JOptionPane.showInputDialog(frame, "Vad får " + guessName + "?").trim().toLowerCase();

		// These two are only used for checking correctness of answers
		String correctName = secret.getKey().toLowerCase();
		String correctGift = secret.getValue().toLowerCase();

		String response;
		if (guessName.equals(correctName) && guessGift.equals(correctGift))
			response = "Grattis! Du hade alla rätt!";
		else if (guessName.equals(correctName))
			response = "Rätt namn, men fel klapp. Rätt klapp var " + secret.getValue() + ".";
		else if (guessGift.equals(correctGift))
			response = "Rätt klapp, men fel namn. Rätt namn var " + secret.getKey() + ".";
		else
			response = "Tyvärr, inga rätt. Rätt svar var: " + secret.getKey() + " får " + secret.getValue() + ".";

		JOptionPane.showMessageDialog(frame, response);
	}
	
	/**
	 * Generate a map of name -> gift pairs.
	 * @return The generated map
	 */
	private Map<String, String> generateAssociations(String[] nameArr, String[] giftArr, int difficulty) {
		List<String> names = Arrays.asList(nameArr), gifts = Arrays.asList(giftArr);

		// Shrink to match difficulty
		names = randomFilter(names, difficulty);
		gifts = randomFilter(gifts, difficulty);

		// Show lists to user
		displayLists(names.toArray(new String[names.size()]), gifts.toArray(new String[names.size()]));

		Collections.shuffle(names, rand);
		Collections.shuffle(gifts, rand);

		// Fill the hashmap
		Map<String, String> assoc = new HashMap<String, String>();
		for (int i = 0; i < names.size(); i++)
			assoc.put(names.get(i), gifts.get(i));

		return assoc;
	}

	/**
	 * Removes elements from the list randomly to match the new size.
	 * 
	 * @param list
	 *            The list to filter
	 * @param newSize
	 *            The target length of the list
	 * @return The new list
	 */
	private List<String> randomFilter(List<String> list, int newSize) {
		ArrayList<String> newList = new ArrayList<String>(list);
		while (newList.size() > newSize)
			newList.remove(rand.nextInt(newList.size()));
		return newList;
	}

	private void displayLists(String[] names, String[] gifts) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append(listWithHeader("Names:", names));
		sb.append(listWithHeader("Gifts:", gifts));
		sb.append("</html>");
		JOptionPane.showMessageDialog(frame, sb);
	}

	private String listWithHeader(String header, String[] list) {
		StringBuilder sb = new StringBuilder();
		sb.append("<h3>").append(header).append("</h3><ul>");

		for (String str : list) {
			sb.append("<li>").append(str).append("</li>");
		}

		sb.append("</ul>");
		return sb.toString();
	}

}
