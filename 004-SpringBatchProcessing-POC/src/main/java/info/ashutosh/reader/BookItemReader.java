package info.ashutosh.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component
public class BookItemReader implements ItemReader<String> {

	private String[] books = new String[] { "Complete Reffrece of Java", "Thinking In Java", "Effective Java",
			"Head first java", "Head First Design Patern" };
	private int counter = 0;

	public BookItemReader() {
		System.out.println("BookItemReader.BookItemReader()");
	}

	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

		System.out.println("BookItemReader.read()");
		if (counter < books.length) {
			return books[counter++];
		}

		return null;
	}

}
