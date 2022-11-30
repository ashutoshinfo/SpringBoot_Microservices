package info.ashutosh;

import org.springframework.batch.item.ItemProcessor;

public class BookItemrocesser implements ItemProcessor<String, String> {

	public BookItemrocesser() {
		System.out.println("BookItemrocesser.BookItemrocesser()");
	}

	@Override
	public String process(String item) throws Exception {
		System.out.println("BookItemrocesser.process()");
		String bookWithWriter = null;
		if (item.equalsIgnoreCase("Complete Reffrece of Java")) {
			bookWithWriter = item + " By Author 1";

		} else if (item.equalsIgnoreCase("Thinking In Java")) {
			bookWithWriter = item + " By Author 2";

		} else if (item.equalsIgnoreCase("Effective Java")) {
			bookWithWriter = item + " By Author 3";

		} else if (item.equalsIgnoreCase("Head first java")) {
			bookWithWriter = item + " By Author 4";

		} else if (item.equalsIgnoreCase("Head First Design Patern")) {
			bookWithWriter = item + " By Author 5";
		}
		return bookWithWriter;
	}

}
