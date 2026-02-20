
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import java.io.File;

public class BugJira {
	public static void main(String[] args) { // TODO Auto-generated method stub
		RestAssured.baseURI = "https://surajpawar1379.atlassian.net/";
		String createIssueResponse = given().header("Content-Type", "application/json").header("Authorization",
				"Basic c3VyYWpwYXdhcjEzNzlAZ21haWwuY29tOkFUQVRUM3hGZkdGMDRwNzlfbFBRYzU2YWtXMmZKdk4yb2d6ZVE4d0pxQ1NpSzcxSVhlampzUHNWNG1GUVlHcTY3TkFxODUyVjM4b0U4dVE5Y1pqYjZTZjNYeGt5VkdwWm1Td2FLeTZRV2wybGE1ZU9fSngwcDFVU3pHaUxFMEI3U1NYN1FqVWRnVTFYc1BuYThlS3pqNEJ5UVhLaTBQWTVJUkNySzNfQ3pyTVNlMk1PRW9aeVBZVT03MTdGOTI3Qg==")
				.body("{\r\n" + "    \"fields\": {\r\n" + "       \"project\":\r\n" + "       {\r\n"
						+ "          \"key\": \"SCRUM\"\r\n" + "       },\r\n"
						+ "       \"summary\": \"creating bug by automation\",\r\n" + "       \"issuetype\": {\r\n"
						+ "          \"name\": \"Bug\"\r\n" + "       }\r\n" + "   }\r\n" + "}\r\n" + "")
				.log().all().post("rest/api/3/issue").then().log().all().assertThat().statusCode(201)
				.contentType("application/json").extract().response().asString();
		JsonPath js = new JsonPath(createIssueResponse);
		String issueId = js.getString("id");
		System.out.println(issueId);

		given().pathParam("key", issueId).header("X-Atlassian-Token", "no-check").header("Authorization",
				"Basic c3VyYWpwYXdhcjEzNzlAZ21haWwuY29tOkFUQVRUM3hGZkdGMDRwNzlfbFBRYzU2YWtXMmZKdk4yb2d6ZVE4d0pxQ1NpSzcxSVhlampzUHNWNG1GUVlHcTY3TkFxODUyVjM4b0U4dVE5Y1pqYjZTZjNYeGt5VkdwWm1Td2FLeTZRV2wybGE1ZU9fSngwcDFVU3pHaUxFMEI3U1NYN1FqVWRnVTFYc1BuYThlS3pqNEJ5UVhLaTBQWTVJUkNySzNfQ3pyTVNlMk1PRW9aeVBZVT03MTdGOTI3Qg==")
				.multiPart("file", new File("C:/Users/suraj/OneDrive/Pictures/Screenshots/screen.png")).log().all()
				.post("rest/api/3/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);
		// Add attachment }
	}
}