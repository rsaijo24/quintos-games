package study;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Scanner;

public class BankAccount {
	private int balance;
	private int account;
	private int pin;
	private String date;
	private ArrayList<Integer>  transaction;
	private boolean firstLog;

	public BankAccount() {
		balance = 0;
		account = 000000;
		pin = 0000;
		date = "03/19/2022";
		transaction = new ArrayList<Integer>();
		firstLog = true;
	}

	public void getStarted(){
		Scanner sc = new Scanner(System.in);
		int accountInput = 000000;
		int pinInput = 0000;
		System.out.println("");
		while (accountInput < 100000 || accountInput > 999999) {
			System.out.println("Please create a 6 digit account number:");
			accountInput = sc.nextInt();
		}
		account = accountInput;
		System.out.println("Successfully assigned your account number.");
		while (pinInput < 1000 || pinInput > 9999) {
			System.out.println("\nPlease created a 4 digit account pin:");
			pinInput = sc.nextInt();
		}
		pin = pinInput;
		System.out.println("Successfully assigned your account pin.");
	}

	public void setPin() {
		Scanner sc = new Scanner(System.in);
		boolean loggedIn = false;
		boolean success = false;
		int accountInput = 000000;
		int newPinInput = 0000;
		int oldPinInput = 0000;
		int attempt = 1;
		while (!loggedIn) {
			System.out.println("\nPlease enter your account number:");
			accountInput = sc.nextInt();
			if (accountInput == account) {
				loggedIn = true;
				System.out.println("Succesfully logged in.");
				while (newPinInput < 1000 || newPinInput > 9999) {
					System.out.println("\nPlease create a 4 digit account pin:");
					newPinInput = sc.nextInt();
				}
				System.out.println("");
				while (attempt <= 3) {
					System.out.println("Attempt " + attempt + "/3: Please enter your old pin to confirm the new change:");
					oldPinInput = sc.nextInt();
					if (oldPinInput == pin) {
						pin = newPinInput;
						attempt = 4;
						success = true;
					}
					attempt++;
				}
				if (success) {
					System.out.println("Your new change was confirmed.");
				}
				else {
					System.out.println("Please try again later.");
				}
			}
			else {
				System.out.println("That is an incorrect account number.");
			}
		}
	}

	public void addTransaction(int amount) {
		balance += amount;
		transaction.add(amount);
	}

	public String viewTransactions() {
		String transactionsStr = "\nTransactions: ";
		for (int i = 0; i < transaction.size(); i++) {
			transactionsStr += (transaction.get(i) + " ");
		}
		return transactionsStr;
	}

	public String getBalance() {
		String balanceStrAbs = Math.abs(balance) + "";
		if (balance >= 0) {
			return "\nYour balance is: $" + balanceStrAbs + ".";
		}
		else if (balance < 0) {
			return  "\nYour balance is -$" + balanceStrAbs + ".";
		}
		return "";
	}
	
	public String getAccount() {
		return "Your account number is: " + account;
	}
	public static void main(String[] args) {
		BankAccount bank0 = new BankAccount();
		bank0.getStarted();
		bank0.addTransaction(100);
		bank0.addTransaction(50);
		bank0.addTransaction(-200);
		bank0.addTransaction(1000);
		bank0.addTransaction(25);
		bank0.addTransaction(-10000);
		System.out.println(bank0.getBalance());
		System.out.println(bank0.viewTransactions());
	}
}
