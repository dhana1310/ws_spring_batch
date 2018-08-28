package com.dhana1310.springbatch.batch.reader;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import com.dhana1310.springbatch.entity.User;
import com.dhana1310.springbatch.listener.JobListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BatchReader {

	@Value("${input}")
	Resource resource;

	@Bean
	public JdbcCursorItemReader<User> readerFromDB(DataSource dataSource) {
		JdbcCursorItemReader<User> reader = new JdbcCursorItemReader<User>();
		reader.setDataSource(dataSource);
		reader.setSql("SELECT * FROM user");
		reader.setRowMapper(new BeanPropertyRowMapper<>(User.class));

		log.info("In Reader");
		return reader;
	}

	//@Bean
	public FlatFileItemReader<User> reader() {

		return new FlatFileItemReaderBuilder<User>().name("personItemReader")
				// .resource(new ClassPathResource("users.csv"))
				.resource(resource).delimited().names(new String[] { "id", "name", "dept", "salary" }).linesToSkip(1)
				.fieldSetMapper(new BeanWrapperFieldSetMapper<User>() {
					{
						setTargetType(User.class);
					}
				}).build();
	}
}
