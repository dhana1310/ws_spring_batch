package com.techprimers.springbatchexample1.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.techprimers.springbatchexample1.entity.User;
import com.techprimers.springbatchexample1.listener.JobListener;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

	@Value("${input}") 
	Resource resource;
	
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

	@Bean
	 public JdbcCursorItemReader<User> readerFromDB(DataSource dataSource){
	  JdbcCursorItemReader<User> reader = new JdbcCursorItemReader<User>();
	  reader.setDataSource(dataSource);
	  reader.setSql("SELECT * FROM user");
	  reader.setRowMapper(new BeanPropertyRowMapper<>(User.class));
	  
//	  reader.setRowMapper(new UserRowMapper());
	  System.err.println("In Reader");
	  return reader;
	 }
	
	//@Bean
	public JdbcBatchItemWriter<User> jdbcBatchItemWriter(DataSource dataSource) {
		
		System.err.println("In Writer new bean");
		return new JdbcBatchItemWriterBuilder<User>().dataSource(dataSource)
	//	.sql("INSERT INTO user ( name, dept, salary, time) VALUES (:name, :dept, :salary, :time)")
		.sql("UPDATE user SET salary = :salary WHERE id = :id")
		.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<User>())
		.build();
		
	}
	
	//@Bean
    public FlatFileItemReader<User> reader() {
		
        return new FlatFileItemReaderBuilder<User>()
            .name("personItemReader")
            //.resource(new ClassPathResource("users.csv"))
            .resource(resource)
            .delimited()
            .names(new String[]{"id", "name", "dept", "salary"})
            .linesToSkip(1)
            .fieldSetMapper(new BeanWrapperFieldSetMapper<User>() {{
                setTargetType(User.class);
            }})
            .build();
    }

}
