package info.ashutosh.configure;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import info.ashutosh.entity.Customer;
import info.ashutosh.processor.CustoemrInfoItemProcessor;
import info.ashutosh.reposetory.CustomerReposetory;

@Configuration
@EnableBatchProcessing
//@AllArgsConstructor //  it will generate a constructor of the fields. Basically spring need to inject those type to create this 'BatchProcessing' class Object. We can use @Autowired 
public class BatchProcessing {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private CustomerReposetory customerReposetory;

	@Autowired
	private CustoemrInfoItemProcessor custoemrInfoItemProcessor;

	@Bean
	FlatFileItemReader<Customer> flatFileItemReader() {
		FlatFileItemReader<Customer> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setResource(new FileSystemResource("F:\\Spring_Microservices\\Microservices\\006-SpringBatchProcessing-CSV-to-Database-2\\src\\main\\resources\\Customer_Data.csv"));
		flatFileItemReader.setName("CSV-Readder");
		//flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		return flatFileItemReader;

	}

	private LineMapper<Customer> lineMapper() {
		DefaultLineMapper<Customer> defaultLineMapper = new DefaultLineMapper<>();
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer());
		defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper());
		return defaultLineMapper;
	}

	private BeanWrapperFieldSetMapper<Customer> beanWrapperFieldSetMapper() {
		BeanWrapperFieldSetMapper<Customer> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
		beanWrapperFieldSetMapper.setTargetType(Customer.class);
		return beanWrapperFieldSetMapper;
	}

	private DelimitedLineTokenizer delimitedLineTokenizer() {
		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setDelimiter(",");
		delimitedLineTokenizer.setStrict(true);
		delimitedLineTokenizer.setNames("id", "firstName", "lastName", "email", "gender", "mobile", "country",
				"dateOfBirth");
		return delimitedLineTokenizer;
	}

	@Bean
	RepositoryItemWriter<Customer> repositoryItemWriter() {
		RepositoryItemWriter<Customer> repositoryItemWriter = new RepositoryItemWriter<>();
		repositoryItemWriter.setRepository(customerReposetory);
		repositoryItemWriter.setMethodName("save");
		return repositoryItemWriter;

	}

	@Bean(name = "step1")
	Step step_1() {
		return stepBuilderFactory.get("step1")
				.<Customer, Customer>chunk(100)
				.reader(flatFileItemReader())
				.processor(custoemrInfoItemProcessor)
				.writer(repositoryItemWriter())
				.taskExecutor(taskExecutor())
				.build();
	}
	
	@Bean(name = "job1")
	Job job_1() {
		return jobBuilderFactory.get("importCustomer")
				.flow(step_1())
				.end()
				.build();
	}
	
	@Bean
	TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor simpleAsyncTaskExecutor=new SimpleAsyncTaskExecutor();
		simpleAsyncTaskExecutor.setConcurrencyLimit(10);
		return simpleAsyncTaskExecutor;
	}

}
