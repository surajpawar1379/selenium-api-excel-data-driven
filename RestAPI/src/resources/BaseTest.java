package resources;

import com.aventstack.extentreports.ExtentReports;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    protected static ExtentReports extent;

    // Shared request specs — test classes can use these directly
    protected static RequestSpecification googleMapsSpec;
    protected static RequestSpecification ecomSpec;
    protected static RequestSpecification librarySpec;

    @BeforeSuite
    public void suiteSetup() {
        extent = ExtentManager.getInstance();

        googleMapsSpec = new RequestSpecBuilder()
                .setBaseUri(ConfigReader.get("google.maps.base.uri"))
                .addQueryParam("key", ConfigReader.get("google.maps.api.key"))
                .setContentType(ContentType.JSON)
                .build();

        ecomSpec = new RequestSpecBuilder()
                .setBaseUri(ConfigReader.get("ecommerce.base.uri"))
                .setContentType(ContentType.JSON)
                .build();

        librarySpec = new RequestSpecBuilder()
                .setBaseUri(ConfigReader.get("library.base.uri"))
                .setContentType(ContentType.JSON)
                .build();
    }

    @AfterSuite
    public void suiteFlush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
