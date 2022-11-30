package info.ashutosh.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

public class BookoItemWriter implements ItemWriter<String> {

	@Override
	public void write(Chunk<? extends String> chunk) throws Exception {

		System.out.println("BookoItemWriter.write()");
		System.out.println("Processed Book " + chunk);
	}

}
