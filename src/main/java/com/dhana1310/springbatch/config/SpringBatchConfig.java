package com.dhana1310.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dhana1310.springbatch.entity.User;
import com.dhana1310.springbatch.listener.JobListener;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

	@Autowired
	JobListener listener;

	@Autowired
	JobBuilderFactory jobBuilderFactory;

	@Autowired
	StepBuilderFactory stepBuilderFactory;

	@Autowired
	ItemReader<User> itemReader;

	@Autowired
	ItemProcessor<User, User> itemProcessor;

	@Autowired
	ItemWriter<User> itemWriter;

	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory) {
		return jobBuilderFactory.get("ETL-Load")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.start(getStepOne()).build();
	}

	@Bean
	public Step getStepOne() {

		return stepBuilderFactory.get("ETL-file-load")
				.<User, User>chunk(100)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(itemWriter)
				.build();
	}

}
