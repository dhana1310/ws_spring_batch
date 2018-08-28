package com.dhana1310.springbatch.batch.writer;

import javax.sql.DataSource;

import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.dhana1310.springbatch.entity.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BatchWriter {

    @Bean
  	public JdbcBatchItemWriter<User> jdbcBatchItemWriter(DataSource dataSource) {
  		
  		log.info("In Writer new bean");
  		return new JdbcBatchItemWriterBuilder<User>().dataSource(dataSource)
  		//.sql("INSERT INTO user ( name, dept, salary, time) VALUES ( :name, :dept, :salary, :time)")
  		.sql("UPDATE user SET salary = :salary WHERE id = :id")
  		.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<User>())
  		.build();
  		
  	}
    
}
