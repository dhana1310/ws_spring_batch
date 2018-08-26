package com.techprimers.springbatchexample1.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "user")
@Entity
@Data
public class User {

	@Id
	private Integer id;
	private String name;
	private String dept;
	private Integer salary;
	private Date time;

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("User{");
		sb.append("id=").append(id);
		sb.append(", name='").append(name).append('\'');
		sb.append(", dept='").append(dept).append('\'');
		sb.append(", salary=").append(salary + "\ntime=" + time);
		sb.append('}');
		return sb.toString();
	}
}
