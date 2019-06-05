import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

@FixMethodOrder
public class TestRestApi {

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 7000;
    }


    @Test
    public void testToSaveUsers() {
        List<Map> list=new ArrayList<>();
        Map<String, String> payload = new HashMap<>();
        payload.put("accName", "Tanvi Gowda");
        payload.put("amount", "5000");
        list.add(payload);
        Map<String, String> payload1 = new HashMap<>();
        payload1.put("accName", "Shivam Gowda");
        payload1.put("amount", "1000");
        list.add(payload1);
        for (Map m: list) {
            String responseString = given().contentType("application/json")
                    .body(m)
                    .when()
                    .post("/create-account")
                    .then()
                    .assertThat()
                    .extract().asString();
            System.out.println(responseString);
        }
    }
    @Test
    public void testToGetAccountHolders() {
        String responseString=get("/fetch-account-holders").then().extract().asString();
        System.out.println(responseString);
    }
    @Test
    public void testToTransferMoney() {
        Map<String, String> request = new HashMap<>();
        request.put("fromAccNo", "1");
        request.put("toAccNo", "2");
        request.put("amount", "1000");
        String responseString= given().contentType("application/json")
                .body(request)
                .when()
                .post( "/transfer-money")
                .then()
                .assertThat()
                .extract().asString()
                ;
        System.out.println(responseString);
    }

    @Test
    public void testToInsufficientBalanceWhileTransferingMoney() {
        Map<String, String> request = new HashMap<>();
        request.put("fromAccNo", "1");
        request.put("toAccNo", "2");
        request.put("amount", "5000");
        String responseString= given().contentType("application/json")
                .body(request)
                .when()
                .post( "/transfer-money")
                .then()
                .assertThat()
                .extract().asString()
                ;
        System.out.println(responseString);
    }
    @Test
    public void testToGetIndividuaAccountHolders() {
        String responseString=get("/fetch-individual-account/1").then().extract().asString();
        System.out.println(responseString);
    }
}