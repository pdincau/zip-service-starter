package acceptance;

import org.junit.Test;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItem;

public class ZipInfoUAT {

    @Test
    public void retrieves_zip_info() {
        RequestSpecification requestSpec = new RequestSpecBuilder().
                setBaseUri(endpoint()).
                setPort(port()).
                setContentType(ContentType.JSON).
                build();

        given().
                spec(requestSpec).
                when().
                get("/zip/us/90210").
                then().
                assertThat().
                body("places.'place name'", hasItem("Beverly Hills"));
    }

    public String endpoint() {
        return "http://" + System.getProperty("host", "localhost");
    }

    public Integer port() {
        String port = System.getProperty("port", "8080");
        return Integer.valueOf(port);
    }
}
