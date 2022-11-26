package info.ashutosh;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FixedRate {

	private static int count = 1;

	/**
	 * fixedRate Explanation. Assume You are a Boss,You will be Allocate for one
	 * task in frequent time frame(10 second) and you have to complete that task in
	 * specific time frame(10 seconds). Now, as a boss you want do this task on your
	 * own instead you assign those task to your employee. Not just because of you
	 * are boss and you have to assign task to your employees who are work under
	 * you,but you have other task to route too. So you have one scheme for your
	 * employees for those Tasks. The Scheme is like this,you gave one task to your
	 * employee and tell them that they have to finish that task in so and so time
	 * frame. Let's say 10 second, and also told them that if they finishes early
	 * let's say in 4 second than remaining (6 seconds) time of that task is their
	 * free time (They can use as a break). but if task takes more time to complete
	 * and exceeds the time limit then they obviously can't take break. and
	 * meanwhile the next task is already on the door (at Boss Desk).so employee can
	 * take break if they finishes early or if time consumption is more than permits
	 * then they will be allocated for next task immediately.
	 */
	@Scheduled(initialDelay = 5000, fixedRate = 10000)
	public void work() {
		// task execution logic
		System.out.println("FixedRate.work() - 10 seconds" + new Date() + " Starts");

		try {
			if (count % 2 == 0) {
				System.err.println("15 second sleep FixedRate");
				Thread.sleep(15000);
				count++;
			} else {
				System.err.println("5 second sleep FixedRate");
				Thread.sleep(5000);
				count++;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("FixedRate.work()" + new Date() + " Ended");

	}
}