package info.ashutosh;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Cron_Expresion {

	// * - any/ all/ every
	// / - To specify Period of Time
	// , - To specify the Posible list of value
	// - - To specify range of values
	// ? - any (can be used only in date and week day when month is secified)
	// L - To specify last days info (can be used only in Date and weeks day fields)
	// W - To specify Week day info (can be used inly in Date and Week day fields)
	// @ - To work with @yearly, @hourly, @monthly, @daily and etc..
	// # - As a combination symbol
	@Scheduled(cron = "40-59,0-10 * * * * *")
	public void work() {
		// task execution logic
		System.out.println("Cron_Expresion.work()");
		// Local 1 000 9
	}
}
