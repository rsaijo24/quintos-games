package games_java.GuessTheNumber;

import java.util.Scanner;

public class GuessTheNumber {
	public static void main(String[] args) {
		// create the user input scanner object
		Scanner sc = new Scanner(System.in);
    boolean again = true;
    while (again == true){
      int num = (int)(Math.random() * 100) + 1;
      int guess = 0;
    
      for (int attempt = 0; guess != num; attempt++)
      {
        if (attempt > 7) {
          System.out.println("Too many guesses!");
          break;
        }
        System.out.println("Guess the number 1-100: ");
        guess = sc.nextInt();
        if (guess > 100 || guess < 1){
          System.out.println("Invalid guess!");
          attempt--;
        }
        else if (guess > num)
        {
          System.out.println("That's too high!");
        }
        else if (guess < num)
        {
          System.out.println("That's too low!");
        }
        else
        {
          System.out.println("That's correct!");
        }
      }
      System.out.println("Play again?");
      String play = sc.nextLine();
      if (play == "No") {
        again = false;
      }
    }
		sc.close(); // close the scanner
		System.exit(0); // exit the program
	}
}
