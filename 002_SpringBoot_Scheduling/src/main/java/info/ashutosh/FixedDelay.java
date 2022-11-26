package info.ashutosh;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FixedDelay {

	private static int count = 1;

	/**
	 * Method execution can take much time as it need, but one execution completes
	 * it must wait for fixed amount of delay.
	 */
	@Scheduled(initialDelay = 5000, fixedDelay = 10000)
	public void work() {
		// task execution logic
		System.out.println("FixedDelay.work() - 10 seconds" + new Date() + " Starts");

		try {
			if (count % 2 == 0) {
				System.err.println("15 second sleep FixedDelay");
				Thread.sleep(15000);
				count++;
			} else {
				System.err.println("5 second sleep FixedDelay");
				Thread.sleep(5000);
				count++;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("FixedDelay.work()" + new Date() + " Ended");
	}
}