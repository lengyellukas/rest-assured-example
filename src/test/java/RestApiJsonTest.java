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

/**
 * example of testing rest API using data provider and request/response
 */

public class RestApiJsonTest {

    private static RequestSpecification request;
    private static ResponseSpecification response;

    //data provider with 4 test sets from European countries
    @DataProvider(name="placesProviderEurope")
    public static Object[][] postCodeAndPlaceEurope() {
        return new Object[][] {
                {"sk", "972 15", "Kľačno"},
                {"be", "1000", "Bruxelles"},
                {"fr", "67000", "Strasbourg"},
                {"de", "70771", "Leinfelden-Echterdingen"}
        };
    }

    //data provider with 4 test sets from Slovakia
    @DataProvider(name="placesProviderSlovakia")
    public static Object[][] postCodeAndPlaceSlovakia() {
        return new Object[][] {
                {"sk", "972 15", "Kľačno"},
                {"sk", "971 01", "Prievidza"},
                {"sk", "900 25", "Chorvátsky Grob"}
        };
    }

    //request specification to set base URI
    @BeforeClass
    public static void createRequestSpecification() {
        request = new RequestSpecBuilder().
                setBaseUri("http://zippopotam.us").
                build();
    }

    //response specification to check status code and type of response in each test
    @BeforeClass
    public static void createResponseSpecification() {
        response = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                build();
    }

    //test the place name returned by post code
    @Test(dataProvider="placesProviderEurope")
    public void checkPlaceNameFoundByPostCode(String country, String postCode, String placeName) {
        given().spec(request).
                pathParam("country", country).pathParam("postCode", postCode).
                when().get("{country}/{postCode}").
                then().spec(response).assertThat().body("places[0].'place name'", equalTo(placeName));
    }

    //checking only status code of giving URI, the same test condition as in RequestSpecification can be used
    @Test(dataProvider="placesProviderEurope")
    public void requestByPostCode_checkStatusCode(String country, String postCode, String placeName) {

        given().spec(request).
                pathParam("country", country).pathParam("postCode", postCode).
                when().
                get("{country}/{postCode}").
                then().spec(response).
                assertThat().
                statusCode(200);
    }


    //check that the state is empty (states are not used in Slovakia)
    @Test(dataProvider="placesProviderSlovakia")
    public void requestSKpostCode_checkStateEmpty(String country, String postCode, String placeName) {
        given().spec(request).
                pathParam("country", country).pathParam("postCode", postCode).
                when().get("{country}/{postCode}").then().assertThat().
                body("places[0].state", isEmptyOrNullString());
    }


}
