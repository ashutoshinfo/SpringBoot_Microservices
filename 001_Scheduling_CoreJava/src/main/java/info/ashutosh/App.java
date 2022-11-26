package info.ashutosh;

import java.util.Date;
import java.util.Timer;

import info.ashutosh.job.Task1;
import info.ashutosh.job.Task2;

/**
 * 
 * @author ashutoshjp
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");
		Timer timer = new Timer();
		timer.schedule(new Task1(), 3000, 2000);

		Timer timer2 = new Timer();
		timer2.schedule(new Task2(), new Date(122, 10, 26, 19, 14, 0), 2000);
	}
}
