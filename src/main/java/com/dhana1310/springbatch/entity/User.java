package com.dhana1310.springbatch.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "user")
@Entity
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;
	private String dept;
	private Integer salary;
	private Date time;

	@Override
	public String toString() {
		final StringBuilder  sb = new StringBuilder ("User{");
		sb.append("id=").append(id);
		sb.append(", name='").append(name).append('\'');
		sb.append(", dept='").append(dept).append('\'');
		sb.append(", salary=").append(salary + ", time='" + time+"'");
		sb.append('}');
		return sb.toString();
	}
}
