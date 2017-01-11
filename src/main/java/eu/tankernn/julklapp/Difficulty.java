package eu.tankernn.julklapp;

public enum Difficulty {
	EASY(3, "Lätt"), MEDIUM(6, "Medel"), HARD(9, "Svår");
	
	public final String name;
	public final int pairs;
	
	private Difficulty(int pairs, String name) {
		this.pairs = pairs;
		this.name = name;
	}
	
	public String toString() {
		return name + ", " + pairs + " par.";
	}
}
