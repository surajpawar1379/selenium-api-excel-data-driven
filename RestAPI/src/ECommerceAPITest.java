import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetail;
import pojo.Orders;
import resources.APIResources;
import resources.BaseTest;
import resources.ConfigReader;

public class ECommerceAPITest extends BaseTest {

    @Test
    public void eCommerceWorkflowTest() {

        // ── Login ──────────────────────────────────────────────────────────
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail(ConfigReader.get("ecommerce.user.email"));
        loginRequest.setUserPassword(ConfigReader.get("ecommerce.user.password"));

        LoginResponse loginResponse = given().relaxedHTTPSValidation().log().all()
                .spec(ecomSpec)
                .body(loginRequest)
                .when().post(APIResources.ECOM_LOGIN.getResource())
                .then().log().all()
                .extract().response().as(LoginResponse.class);

        String token  = loginResponse.getToken();
        String userId = loginResponse.getUserId();
        Assert.assertNotNull(token,
                "Login failed — check ecommerce.user.email / ecommerce.user.password in config.properties. " +
                "API message: " + loginResponse.getMessage());

        // ── Add Product ────────────────────────────────────────────────────
        RequestSpecification authedSpec = new RequestSpecBuilder()
                .setBaseUri(ConfigReader.get("ecommerce.base.uri"))
                .addHeader("authorization", token)
                .build();

        String addProductResponse = given().relaxedHTTPSValidation().log().all()
                .spec(authedSpec)
                .param("productName",        "Laptop")
                .param("productAddedBy",     userId)
                .param("productCategory",    "fashion")
                .param("productSubCategory", "shirts")
                .param("productPrice",       "11500")
                .param("productDescription", "Lenovo")
                .param("productFor",         "men")
                .multiPart("productImage", new File(ConfigReader.get("product.image.file")))
                .when().post(APIResources.ECOM_ADD_PRODUCT.getResource())
                .then().log().all()
                .extract().response().asString();

        String productId = new JsonPath(addProductResponse).get("productId");

        // ── Create Order ───────────────────────────────────────────────────
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCountry("India");
        orderDetail.setProductOrderedId(productId);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(orderDetail);
        Orders orders = new Orders();
        orders.setOrders(orderDetailList);

        RequestSpecification authedJsonSpec = new RequestSpecBuilder()
                .setBaseUri(ConfigReader.get("ecommerce.base.uri"))
                .addHeader("authorization", token)
                .setContentType(ContentType.JSON)
                .build();

        given().relaxedHTTPSValidation().log().all()
                .spec(authedJsonSpec)
                .body(orders)
                .when().post(APIResources.ECOM_CREATE_ORDER.getResource())
                .then().log().all();

        // ── Delete Product ─────────────────────────────────────────────────
        String deleteResponse = given().relaxedHTTPSValidation().log().all()
                .spec(authedJsonSpec)
                .pathParam("productId", productId)
                .when().delete(APIResources.ECOM_DELETE_PRODUCT.getResource())
                .then().log().all()
                .extract().response().asString();

        Assert.assertEquals(new JsonPath(deleteResponse).getString("message"),
                "Product Deleted Successfully");
    }
}
