import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RestApiXmlTest {

    private static RequestSpecification request;
    private static ResponseSpecification response;

    //data provider with one book
    @DataProvider(name="bookProvider")
    public static Object[][] provideBookNumber() {
        return new Object[][] {
                {"GB98Y6084.xml", "Harry Potter and the chamber of secrets / J. K. Rowling", "http://bnb.data.bl.uk/id/resource/012310990"}
        };
    }


    //request specification to set base URI
    @BeforeClass
    public static void createRequestSpecification() {
        request = new RequestSpecBuilder().
                setBaseUri("http://bnb.data.bl.uk/doc/resource").
                build();
    }

    //response specification to check status code and type of response in each test
    @BeforeClass
    public static void createResponseSpecification() {
        response = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.XML).
                build();
    }


    //checking if the book num returns Harry Potter book
    @Test(dataProvider = "bookProvider")
    public void checkLabel(String id, String label, String href) {
        given().spec(request).pathParam("id", id).
                when().get("{id}").then().
                assertThat().body("result.primaryTopic.sameAs.label", containsString(label));
    }

    //check of link to book performed on the href attribute
    @Test(dataProvider = "bookProvider")
    public void checkAttribute(String id, String label, String href) {
            given().spec(request).pathParam("id", id).
                    when().get("{id}").then().
                    assertThat().body("result.primaryTopic.sameAs.@href", equalTo(href));
    }
}
