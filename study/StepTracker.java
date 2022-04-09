package study;

public class StepTracker {
	private int requiredSteps;
	private int daysActive;
	private int totalSteps;
	private int totalDays;

	public StepTracker (int requiredSteps) {
		this.requiredSteps = requiredSteps;
		daysActive = 0;
		totalSteps = 0;
		totalDays = 0;
	}

	public void addDailySteps(int steps) {
		totalSteps += steps;
		totalDays++;
		if (steps >= requiredSteps) {
			daysActive++;
		}
	}

	public int activeDays() {
		return daysActive;
	}

	public double averageSteps() {
		if (totalDays == 0) {
			return 0.0;
		}
		return 1.0 * totalSteps / totalDays;
	}

	public static void main(String[] args) {
		StepTracker tr = new StepTracker(10000);
		System.out.println(tr.activeDays());
		System.out.println(tr.averageSteps());
		tr.addDailySteps(9000);
		tr.addDailySteps(15000);
		System.out.println(tr.activeDays());
		System.out.println(tr.averageSteps());
	}
}
