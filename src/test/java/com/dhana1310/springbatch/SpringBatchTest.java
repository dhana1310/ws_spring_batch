package com.dhana1310.springbatch;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.dhana1310.springbatch.entity.User;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringBatchTest {

	@Autowired
	private NamedParameterJdbcTemplate db2NamedParameterJdbcTemplate;	
	
	@LocalServerPort
	private int port;
	
	@Before
	public void setUp() throws Exception {

		RestAssured.port = port;

	}
	
	@Test
	public void testBatchUserUpdate() {
		
		Response response = given().accept(ContentType.JSON).contentType(ContentType.JSON)
				.get("/load");
		
		assertThat(response.statusCode(), is(HttpStatus.SC_OK));
		JsonPath jsonPathEvaluator = response.jsonPath();
		
		List<User> result = db2NamedParameterJdbcTemplate.query("SELECT salary FROM user", new BeanPropertyRowMapper<>(User.class));

		// ASSERT
		assertNotEquals(0, result.size());
		assertTrue(result.get(0).getSalary().equals(15000));
		assertThat(jsonPathEvaluator.get("exitCode"), is("COMPLETED"));
		assertThat(jsonPathEvaluator.get("running"), is(false));
		
	}

}
