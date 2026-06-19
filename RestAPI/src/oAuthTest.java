import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import resources.BaseTest;
import resources.ConfigReader;

public class oAuthTest extends BaseTest {

    @Test
    public void oAuthCourseValidationTest() {

        // ── Obtain access token ────────────────────────────────────────────
        String tokenResponse = given().log().all()
                .formParam("client_id",     ConfigReader.get("oauth.client.id"))
                .formParam("client_secret", ConfigReader.get("oauth.client.secret"))
                .formParam("grant_type",    "client_credentials")
                .formParam("scope",         "trust")
                .when().post(ConfigReader.get("oauth.token.url"))
                .asString();

        String accessToken = new JsonPath(tokenResponse).getString("access_token");

        // ── Fetch course details ───────────────────────────────────────────
        GetCourse gc = given().log().all()
                .queryParam("access_token", accessToken)
                .when().get(ConfigReader.get("oauth.courses.url"))
                .as(GetCourse.class);

        System.out.println("LinkedIn : " + gc.getLinkedIn());
        System.out.println("Instructor: " + gc.getInstructor());

        // ── Find SoapUI API course price ───────────────────────────────────
        List<Api> apiCourses = gc.getCourses().getApi();
        for (Api course : apiCourses) {
            if (course.getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
                System.out.println("SoapUI price: " + course.getPrice());
            }
        }

        // ── Assert WebAutomation course titles ─────────────────────────────
        List<String> actualTitles = new ArrayList<>();
        for (pojo.WebAutomation w : gc.getCourses().getWebAutomation()) {
            actualTitles.add(w.getCourseTitle());
        }

        List<String> expectedTitles = Arrays.asList(
                "Selenium Webdriver Java", "Cypress", "Protractor");

        Assert.assertEquals(actualTitles, expectedTitles,
                "WebAutomation course titles do not match");
    }
}
