package games_java.Hangman;

import java.util.Scanner;

public class Hangman {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		String[] hangman = new String[] { """
				  
				      
				      
				      
				      
				      
				=========""","""
				  
				      
				      
				      |
				      |
				      |
				=========""","""
				  
				      |
				      |
				      |
				      |
				      |
				=========""","""
				  +---+
				      |
				      |
				      |
				      |
				      |
				=========""", """
				  +---+
				  |   |
				      |
				      |
				      |
				      |
				=========""", """
				  +---+
				  |   |
				  O   |
				      |
				      |
				      |
				=========""", """
				  +---+
				  |   |
				  O   |
				  |   |
				      |
				      |
				=========""", """
				  +---+
				  |   |
				  O   |
				 /|   |
				      |
				      |
				=========""", """
				  +---+
				  |   |
				  O   |
				 /|\\  |
				      |
				      |
				=========""", """
				  +---+
				  |   |
				  O   |
				 /|\\  |
				 /    |
				      |
				=========""", """
				  +---+
				  |   |
				  O   |
				 /|\\  |
				 / \\  |
				      |
				=========""" };

		String wordList = "abruptly absurd abyss affix askew avenue awkward axiom azure bagpipes bandwagon banjo bayou beekeeper bikini blitz blizzard boggle bookworm boxcar buckaroo buffalo buffoon buxom buzzard buzzing buzzwords cobweb croquet crypt cycle disavow dizzying duplex dwarves embezzle equip espionage euouae exodus faking fishhook fixable fjord flapjack flopping fluffiness flyby foxglove frazzled frizzled fuchsia funny gabby galaxy galvanize gazebo gizmo glowworm glyph gnarly gnostic gossip grogginess haiku haphazard hyphen icebox injury ivory ivy jackpot jawbreaker jaywalk jazzy jelly jigsaw jinx jiujitsu jockey jogging joking jovial joyful juicy jukebox jumbo kayak kazoo keyhole kilobyte kiosk kitsch kiwifruit klutz knapsack larynx lengths lucky luxury lymph marquee matrix megahertz microwave mnemonic mystify nightclub nowadays oxidize oxygen pajama phlegm pixel pizazz polka psyche puppy puzzling quartz queue quips quiz quizzes quorum razzmatazz rhubarb rhythm scratch snazzy sphinx squawk staff strength stretch stronghold stymied subway swivel syndrome thrift thumb topaz transcript transgress transplant twelfth triphthong unknown unzip vaporize voodoo vortex walkway waltz wave wavy waxy well whomever witch wizard wristwatch xylophone yacht youthful yummy zigzag zilch zipper zodiac zombie";

		/* PART A0: split the words String into an array, choose a random word */
		String[] words = wordList.split(" ");

		// generate random number between 0 and the number of words in the words array
		int randInt = (int) (Math.random() * words.length);
		String word = words[randInt];

		System.out.println(word);

		/* PART A1: make a char array with a line for each letter in the word */
		// Example word: 'quiz'
		// lines -> ['_', '_', '_', '_']
		char[] lines = new char[word.length()];
		for (int counter = 0; counter < word.length(); counter++){
			lines[counter] = '_';
		}
		
		/* PART B1: make the game loop, don't use the hangman until part B */
		int wrongCount = 0;
		while (wrongCount < hangman.length) {
			boolean finish = true;

			for (int counter = 0; counter < word.length(); counter++) {
				if (lines[counter] == '_') {
					finish = false;
				}
			}

			if (finish == true) {
				break;
			}
			
			String joinedLines = "";
			for (char letter : lines) {
				joinedLines += letter + " ";
			}
			System.out.println(hangman[wrongCount] + joinedLines + "\n\nGuess a letter: ");

			String guess = sc.nextLine();

			if (guess.length() > 1) {
				if (guess.equals(word)) {
					break;
				}

			  else {
					wrongCount++;
				}
			}
			else {
				char guessLetter = guess.charAt(0);

				boolean correct = false;
				for (int i = 0; i < word.length(); i++) {
					if (guessLetter == word.charAt(i)) {
						lines[i] = guessLetter;
						correct = true;
					}
				}
				if (correct == false) {
					wrongCount++;
				}
			}
		}

		if (wrongCount == hangman.length) {
			System.out.println("You Lost!\nThe word was " + word + "!");
		}

		else {
			System.out.println("Congratulations, you won!");
		}

		sc.close();
		System.exit(0);
	}
}
