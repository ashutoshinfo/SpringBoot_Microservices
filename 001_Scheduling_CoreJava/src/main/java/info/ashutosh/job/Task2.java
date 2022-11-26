package info.ashutosh.job;

import java.util.TimerTask;

public class Task2 extends TimerTask {

	@Override
	public void run() {
		System.out.println("Task2.run()" + Thread.currentThread().getName());
	}

}
