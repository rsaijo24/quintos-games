package games_java.QuickClicks;

import static games_java.QuintOS.*;

import java.time.Instant;

public class QuickClicks {
	String target = """
			 .d88b.
			.8P  Y8.
			88    88
			88    88
			'8b  d8'
			 'Y88P'""".substring(1);
	// substring removes the first character from the string
	// in this case I remove the new line at the beginning
	// so the first line of the button will be at the proper
	// row value
	Button btn = null;
	long [] times = new long [10];
	int clicks = 0;

	void makeButton() {
		/* PART A0: change the values of row and col to be random */
		// screen size is 80 cols x 30 rows
		// target is 8w x 6h
		// drawing starts from top left corner
		// we want to draw the target within the bounds of the frame
		// 30 rows - 6 target height - 1 frame line = 23
		// 80 columns - 8 target width - 1 frame line = 71
		if (btn != null) {
			btn.erase();
		}

		if (clicks < 10) {
			times[clicks] = Instant.now().toEpochMilli();
			clicks++;

			int row = (int) (Math.random() * 22) + 1;
			int col = (int) (Math.random() * 71) + 1;
			btn = button(target, row, col, () -> {
				this.makeButton();
			});
		}
		else {
			int [] speeds = new int [9];
			String results = "";
			for (int i = 0; i < 9; i++) {
				speeds[i] = (int) (times[i + 1] - times[i]);
				results += speeds[i] + "\n";
			}
			System.out.println(results);

			int sum = 0;
			int slowest = 0;
			int fastest = speeds[0];

			for (int i = 0; i < speeds.length; i++) {
				sum += speeds[i];
				if (speeds[i] > slowest) {
					slowest = speeds[i];
				}
				if (speeds[i] < fastest) {
					fastest = speeds[i];
				}
			}
			int average = (int) (sum / speeds.length);

			System.out.println("Your average was: " + average + "ms!" +
			"\nYour slowest speed was: " + slowest + "ms!" + "\nYour fastest speed was: " + fastest + "ms!");
		}
	}

	void makeBackground() {
		
	}

	void startGame() {
		alert("Click the targets as fast as you can!", 10, 15);
		makeButton();
	}

	public QuickClicks() {
		startGame();
	}

	public static void main(String[] args) {
		new QuickClicks();
	}
}
