package LiveProject;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.reporters.XMLConstants;

import static io.restassured.RestAssured.given;

public class GitHubTest {
    RequestSpecification reqSpec;
    String token="token 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";

    int id;

    @BeforeClass
    public void setup() {
        reqSpec = new RequestSpecBuilder()
                .setBaseUri("https://api.github.com")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", token)
                .build();
    }

    @Test(priority=1)
    public void postMethod(){
        String requestBody= "{\"title\": \"TestAPIKey\", \"key\": \"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCuX/esDMvbACEabkeWiv5cI2AN1B24mreXk1xCiL2+uJSOuUZ1JpHxfBESfqb4KrHpZvBgDgoHenp/1/WBoia+W0X0ZHQOBWZNonu3fhPlTNRkwAFoer/yN1652uGHfVR58P6jSqum6GMtIVhbqm8b11H9Y73ICnqQ0SQaUNy+uDie3pmVebwUZ4TUkABv7LRDH2Zr9qAq0Mlf494mIq6jvCA4fbofAQMRK48LbWyZjsmYtMOeuqzOAuILNgUsR0B9b8Yt3ewNiyrrEtY9Nq2t7kR/IOz2hZGaNWm4mnIdq5S8ANFm9/9RkqAVA4DWrCpehxwIpdERiv0vRqUX8qNF\"}";
        Response response = given().spec(reqSpec)
                .body(requestBody)
                .when().post("/user/keys");
        id = response.then().extract().path("id");
        System.out.println(response.getBody().asPrettyString());
        response.then().statusCode(201);


    }

    @Test(priority=2)
    public void getMethod(){
        Response response = given().spec(reqSpec)
                .pathParam("keyId", id)
                .when().get("/user/keys/{keyId}");
        System.out.println(response.getBody().asPrettyString());
        response.then().statusCode(200);

    }
    @Test(priority=3)
    public void deleteMethod(){
        Response response = given().spec(reqSpec)
                .pathParam("keyId", id)
                .when().delete("/user/keys/{keyId}");
        System.out.println(response.getBody().asPrettyString());
        response.then().statusCode(204);
    }

}
