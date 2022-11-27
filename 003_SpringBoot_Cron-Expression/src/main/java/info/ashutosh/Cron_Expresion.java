package info.ashutosh;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Cron_Expresion {

	@Scheduled(cron = "40-59,0-10 * * * * *")
	public void work() {
		// task execution logic
		System.out.println("Cron_Expresion.work()");
		// Live 0 1 00
	}
}
