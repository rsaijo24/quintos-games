package study;
public class PracticeOne {
	private int variableOne;
	private boolean variableTwo;
	private char variableThree;
	private int variableFour;

	public PracticeOne() {
		variableOne = 0;
		variableTwo = false;
		variableThree = ' ';
		variableFour = 0;
	
	}
	public PracticeOne(int o, boolean t, char h, int f) {
		variableOne = o;
		variableTwo = t;
		variableThree = h;
		variableFour = f;
	}

	public void addVarOne(int add) {
		variableOne += add;
	}

	public void subVarOne(int sub) {
		variableOne -= sub;
	}

	public int getVarOne() {
		return variableOne;
	}

	public double getAverage() {
		return (variableOne + variableFour) * 1.0 / 2;
	}

	public boolean getVarTwo() {
		return variableTwo;
	}

	public char getVarThree() {
		return variableThree;
	}
	public static void main(String[] args) {
		PracticeOne ex0 = new PracticeOne();
		PracticeOne ex1 = new PracticeOne(5, true, 'X', 20);
		ex0.addVarOne(5);
		ex1.addVarOne(15);
		System.out.println(ex0.getVarOne());
		System.out.println(ex1.getVarOne());
		System.out.println(ex0.getVarTwo());
		System.out.println(ex1.getVarTwo());
		System.out.println(ex0.getAverage());
		System.out.println(ex1.getAverage());
	}
}
