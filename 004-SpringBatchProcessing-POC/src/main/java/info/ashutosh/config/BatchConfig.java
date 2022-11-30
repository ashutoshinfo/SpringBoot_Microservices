package info.ashutosh.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import info.ashutosh.listener.JobMonitoringListener;
import info.ashutosh.processer.BookItemProcesser;
import info.ashutosh.reader.BookItemReader;
import info.ashutosh.writer.BookoItemWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	JobBuilderFactory jobBuilderFactory;

	@Autowired
	StepBuilderFactory stepBuilderFactory;

	@Autowired
	BookItemReader bookItemReader;

	@Autowired
	BookoItemWriter bookoItemWriter;

	@Autowired
	BookItemProcesser bookItemProcesser;

	@Autowired
	JobMonitoringListener jobMonitoringListener;

	@Bean(name = "step1")
	Step createStep1() {
		System.out.println("BatchConfig.createStep1()");
		return stepBuilderFactory.get("step1").<String, String>chunk(2).reader(bookItemReader).writer(bookoItemWriter)
				.processor(bookItemProcesser).build();

	}

	@Bean(name = "job1")
	Job createJob() {
		System.out.println("BatchConfig.createJob()");
		return jobBuilderFactory.get("job1").listener(jobMonitoringListener).incrementer(new RunIdIncrementer())
				.start(createStep1()).build();

	}

}
