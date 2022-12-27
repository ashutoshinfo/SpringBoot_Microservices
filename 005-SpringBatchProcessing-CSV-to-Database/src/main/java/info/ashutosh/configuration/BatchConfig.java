package info.ashutosh.configuration;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import info.ashutosh.listener.JobMonitoringListener;
import info.ashutosh.model.Employee;
import info.ashutosh.processor.EmployeeInfoItemProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	// Create Reader Object
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private JobMonitoringListener jobMonitoringListener;

	@Autowired
	private EmployeeInfoItemProcessor eployeeInfoItemProcessor;

	@Autowired
	private DataSource dataSource;

	@Bean
	FlatFileItemReader<Employee> createFlatFileItemReader() {
		// Create Reader object
		FlatFileItemReader<Employee> flatFileItemReader = new FlatFileItemReader<>();

		// Set CSV File as Resource
		flatFileItemReader.setResource(new ClassPathResource("Emloyee_Details.csv"));

		// Create DelimitedLineTokenizer Object To get Tokens from line
		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setDelimiter(",");
		delimitedLineTokenizer.setNames("e_id", "e_name", "e_address", "e_slary");

		// Create BeanWrapperFieldSetMapper to set the tokens to model class object
		// properties
		BeanWrapperFieldSetMapper<Employee> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
		beanWrapperFieldSetMapper.setTargetType(Employee.class);

		// Create LineMapper Object to get each Line from CSV
		DefaultLineMapper<Employee> defaultLineMapper = new DefaultLineMapper<>();
		//
		defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

		flatFileItemReader.setLineMapper(defaultLineMapper);

		return flatFileItemReader;

	}

	@Bean
	JdbcBatchItemWriter<Employee> batchItemWriter() {
		JdbcBatchItemWriter<Employee> batchItemWriter = new JdbcBatchItemWriter<>();
		batchItemWriter.setDataSource(dataSource);
		batchItemWriter.setSql(
				"INSERT INTO 01_batch_employee_info VALUES (:e_id,:e_name,:e_address,:e_slary,:e_grossSalary,:e_netSalary)");

		BeanPropertyItemSqlParameterSourceProvider<Employee> beanPropertyItemSqlParameterSourceProvider = new BeanPropertyItemSqlParameterSourceProvider<>();
		batchItemWriter.setItemSqlParameterSourceProvider(beanPropertyItemSqlParameterSourceProvider);
		return batchItemWriter;

	}

	@Bean(name = "step1")
	Step step_1() {
		return stepBuilderFactory.get("step1").<Employee, Employee>chunk(5).reader(createFlatFileItemReader())
				.processor(eployeeInfoItemProcessor).writer(batchItemWriter()).taskExecutor(taskExecutor()).build();
	}

	@Bean(name = "job1")
	Job job_1() {
		return jobBuilderFactory.get("job1").listener(jobMonitoringListener).incrementer(new RunIdIncrementer())
				.start(step_1()).build();
	}

	@Bean
	TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
		simpleAsyncTaskExecutor.setConcurrencyLimit(10);
		return simpleAsyncTaskExecutor;
	}

}
