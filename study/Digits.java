package study;

import java.util.ArrayList;

public class Digits {
	private ArrayList<Integer> digitList;
	
	public Digits(int num) {
		digitList = new ArrayList<Integer>();

		if (num == 0) {
			digitList.add(0);
		}

		int divisor = 1;
		while (num > 0) {
			divisor *= 10;
			int remainder = num % divisor;
			int digit = remainder / (divisor / 10);
			digitList.add(0, digit);
			num -= remainder;
		}
	}

	public boolean isStrictlyIncreasing() {
		for (int i = 0; i < digitList.size() - 1; i++) {
			if (digitList.get(i) > digitList.get(i + 1)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		String list = "";
		for (int i = 0; i < digitList.size(); i++) {
			list += i + ": " + digitList.get(i) + '\n';
		}
		list += "\n";
		return list;
	}

	public static void main(String[] args) {
		Digits d0 = new Digits(12345);
		System.out.println(d0);
		System.out.println(d0.isStrictlyIncreasing());

		Digits d1 = new Digits(0);
		System.out.println(d1);

		Digits d2 = new Digits(12354);
		System.out.println(d2);
		System.out.println(d2.isStrictlyIncreasing());
	}
}
