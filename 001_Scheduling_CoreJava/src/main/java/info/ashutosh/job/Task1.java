package info.ashutosh.job;

import java.util.TimerTask;

public class Task1 extends TimerTask {

	@Override
	public void run() {
		System.out.println("Task1.run()" + Thread.currentThread().getName());
	}

}
