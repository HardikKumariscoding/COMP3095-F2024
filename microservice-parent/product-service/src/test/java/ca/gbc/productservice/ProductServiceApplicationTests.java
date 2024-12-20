package ca.gbc.productservice;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

// This annotation is used in combination with TestContainers to automatically configure the connection to
// the Test MongoDbContainer


@LocalServerPort
private Integer port;

@BeforeEach
void setup(){
	RestAssured.defaultParser = Parser.JSON;
	RestAssured.baseURI = "http://localhost";
	RestAssured.port = port;
}



@ServiceConnection
static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

static {
		mongoDBContainer.start();
	}

@Test
void createProductTest() {

	String requestBody = """
            {
            "name" : "Samsung TV",
            "description" : "Samsung TV - Model 2024",
            "price" : 2000
            }
            """;

	// BDD - Behavior Driven Development (Given, When, Then)
	RestAssured.given()
			.contentType("application/json")
			.body(requestBody)
			.when()
			.post("/api/product")
			.then()
			.log().all() // Logs the response for debugging
			.statusCode(201) // Expecting HTTP status 201 for created
			.body("id", Matchers.notNullValue()) // Expecting a non-null id
			.body("name", Matchers.equalTo("Samsung TV")) // Expecting the correct name
			.body("description", Matchers.equalTo("Samsung TV - Model 2024")) // Expecting the correct description
			.body("price", Matchers.equalTo(2000)); // Expecting the correct price
}

	@Test
	void getAllProductsTest() {

		String requestBody = """
			{
			"name" : "Samsung TV",
			"description" : "Samsung TV - Model 2024",
			"price" : 2000
			
			}
			""";

		//BDD - -0 Behavioural Driven Development (Given,When,Then)
		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/product")
				.then()
				.log().all()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("Samsung TV"))
				.body("description", Matchers.equalTo("Samsung TV - Model 2024"))
				.body("price", Matchers.equalTo(2000));

 // We are creating this upper code again in getAllProducts() because these tests will exist seperately from each other
		// IF we don't do this then getAllProducts won't be able to read the product created
		//(because it doesn't exist), and return an empty response.

		RestAssured.given()
				.contentType("application/json")
				.when()
				.get("/api/product")
				.then()
				.log().all()
				.statusCode(200)
				.body("size()", Matchers.greaterThan(0))
				.body("[0].name",Matchers.equalTo("Samsung TV"))
				.body("[0].description", Matchers.equalTo("Samsung TV - Model 2024"))
				.body("[0].price", Matchers.equalTo(2000));








	}

}
